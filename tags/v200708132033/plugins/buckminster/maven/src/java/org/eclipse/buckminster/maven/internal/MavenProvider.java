/*******************************************************************************
 * Copyright (c) 2004, 2006
 * Thomas Hallgren, Kenneth Olwing, Mitch Sonies
 * Pontus Rydin, Nils Unden, Peer Torngren
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the individual
 * copyright holders listed above, as Initial Contributors under such license.
 * The text of such license is available at www.eclipse.org.
 *******************************************************************************/

package org.eclipse.buckminster.maven.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.buckminster.core.CorePlugin;
import org.eclipse.buckminster.core.XMLConstants;
import org.eclipse.buckminster.core.common.model.Documentation;
import org.eclipse.buckminster.core.common.model.Format;
import org.eclipse.buckminster.core.helpers.BuckminsterException;
import org.eclipse.buckminster.core.rmap.model.BidirectionalTransformer;
import org.eclipse.buckminster.core.rmap.model.Provider;
import org.eclipse.buckminster.core.rmap.model.VersionConverterDesc;
import org.eclipse.buckminster.core.version.IVersionConverter;
import org.eclipse.buckminster.sax.ISaxableElement;
import org.eclipse.buckminster.sax.Utils;
import org.eclipse.core.runtime.CoreException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class MavenProvider extends Provider
{
    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[0-9]*$");

    public static final String BM_MAVEN_PROVIDER_NS = XMLConstants.BM_PREFIX + "MavenProvider-1.0";

	public static final String BM_MAVEN_PROVIDER_PREFIX = "mp";

	public static final String ELEM_MAPPINGS = "mappings";

	public static final String ELEM_RULE = "rule";

	/**
	 * Apply default rules. I.e.<ul>
	 * <li>If the component name contains a dot, then separate the group and
	 * artifact using it.</li>
	 * <li>If no dot is found, then use the same name for the group and
	 * artifact</li>
	 * </ul>
	 * Other parsing is performed to detect some problems like artifactId contianing dots
	 * @param name the name of the component
	 * @return an entry with a Maven groupId and artifactId
	 */
	public static MapEntry getDefaultGroupAndArtifact(String name)
	{
	    String groupId = name;
        String artifactId = name;
		int dotIdx = name.lastIndexOf('.');
		if (dotIdx > 0)
		{
		    groupId = name.substring(0, dotIdx);
		    artifactId = name.substring(dotIdx + 1);

		    /* some projects have dots in the artifactId, see http://jira.codehaus.org/browse/MNG-2968
		     * we'll assume that if the portion after the last dot is a number then the artifactId contains a dot */
	        Matcher m = NUMBER_PATTERN.matcher(artifactId);
	        if(m.matches())
	        {
	            dotIdx = groupId.lastIndexOf('.');
	            if (dotIdx > 0)
	            {
	                artifactId = groupId.substring(dotIdx + 1) + "." + artifactId;
	                groupId = groupId.substring(0, dotIdx);
	            }
	        }
		}
		return new MapEntry(name, groupId, artifactId, null);
	}

	/**
	 * Apply default rules, <code>groupId.artifactId</code>
	 * @param groupId the Maven group id
	 * @param artifactId the Maven artifact id
	 * @return The default component name.
	 */
	public static String getDefaultName(String groupId, String artifactId)
	{
		return groupId + '.' + artifactId;
	}

	private final Map<String, MapEntry> m_mappings;

	private final List<BidirectionalTransformer> m_rules;

	public MavenProvider(String remoteReaderType, String componentType, String[] managedCategories,
		VersionConverterDesc versionConverterDesc, Format uri, boolean mutable, boolean source,
		Documentation documentation, Map<String, MapEntry> mappings, List<BidirectionalTransformer> rules)
	{
		super(remoteReaderType, componentType, managedCategories, versionConverterDesc, uri, mutable, source,
			documentation);
		if(mappings == null)
			mappings = Collections.emptyMap();
		if(rules == null)
			rules = Collections.emptyList();
		m_mappings = mappings;
		m_rules = rules;
	}

	@Override
	public void addPrefixMappings(HashMap<String, String> prefixMappings)
	{
		super.addPrefixMappings(prefixMappings);
		prefixMappings.put(BM_MAVEN_PROVIDER_PREFIX, BM_MAVEN_PROVIDER_NS);
	}

	@Override
	public IVersionConverter getVersionConverter() throws CoreException
    {
		return CorePlugin.getDefault().getVersionConverter(IVersionConverter.TAG);
	}

	@Override
	protected void addAttributes(AttributesImpl attrs)
	{
		super.addAttributes(attrs);
		attrs.addAttribute(javax.xml.XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type", "xsi:type",
			"CDATA", BM_MAVEN_PROVIDER_PREFIX + ":MavenProvider");
	}

	@Override
	protected void emitElements(ContentHandler handler, String namespace, String prefix) throws SAXException
	{
		super.emitElements(handler, namespace, prefix);
		if(m_mappings.size() == 0 && m_rules.size() == 0)
			return;

		String qName = Utils.makeQualifiedName(BM_MAVEN_PROVIDER_PREFIX, ELEM_MAPPINGS);
		handler.startElement(BM_MAVEN_PROVIDER_NS, ELEM_MAPPINGS, qName, ISaxableElement.EMPTY_ATTRIBUTES);
		for(MapEntry mapping : m_mappings.values())
			mapping.toSax(handler, BM_MAVEN_PROVIDER_NS, BM_MAVEN_PROVIDER_PREFIX, mapping.getDefaultTag());
		for(BidirectionalTransformer rule : m_rules)
			rule.toSax(handler, BM_MAVEN_PROVIDER_NS, BM_MAVEN_PROVIDER_PREFIX, ELEM_RULE);
		handler.endElement(BM_MAVEN_PROVIDER_NS, ELEM_MAPPINGS, qName);
	}

	String getComponentName(String groupId, String artifactId) throws CoreException
	{
		for(MapEntry me : m_mappings.values())
		{
			if(me.isMatchFor(groupId, artifactId))
				return me.getName();
			
			List<GroupAndArtifact> aliases = me.getAliases();
			int idx = aliases.size();
			while(--idx >= 0)
			{
				GroupAndArtifact alias = aliases.get(idx);
				if(alias.isMatchFor(groupId, artifactId))
					return me.getName();
			}
		}

		if(m_rules.size() > 0)
		{
			String compiled = groupId + '/' + artifactId;
			for(BidirectionalTransformer rule : m_rules)
			{
				String transformed = rule.transformTo(compiled);
				if(transformed != null)
					return transformed;
			}
		}
		return getDefaultName(groupId, artifactId);
	}

	MapEntry getGroupAndArtifact(String name) throws BuckminsterException
	{
		MapEntry entry = m_mappings.get(name);
		if(entry != null)
			return entry;

		String transformed = null;
		for(BidirectionalTransformer rule : m_rules)
		{
			transformed = rule.transformFrom(name);
			if(transformed != null)
				break;
		}

		if(transformed == null)
			return getDefaultGroupAndArtifact(name);

		int slashPos = transformed.indexOf('/');
		if(slashPos < 0)
			throw new BuckminsterException("The result of applying a match rule had no separator slash: "
				+ transformed);

		return new MapEntry(name, transformed.substring(0, slashPos), transformed.substring(slashPos + 1), null);
	}
}
