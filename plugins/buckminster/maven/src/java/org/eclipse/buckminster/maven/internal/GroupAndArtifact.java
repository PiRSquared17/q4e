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

import org.eclipse.buckminster.sax.ISaxableElement;
import org.eclipse.buckminster.sax.Utils;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;


class GroupAndArtifact implements ISaxableElement
{
	public static final String ALIAS_TAG = "alias";
	public static final String ATTR_GROUP_ID = "groupId";
	public static final String ATTR_ARTIFACT_ID = "artifactId";

	private final String m_groupId;
	private final String m_artifactId;

	public GroupAndArtifact(String groupId, String artifactId)
	{
		m_groupId = groupId;
		m_artifactId = artifactId;
	}

	public final String getArtifactId()
	{
		return m_artifactId;
	}

	public String getDefaultTag()
	{
		return ALIAS_TAG;
	}

	public final String getGroupId()
	{
		return m_groupId;
	}

	public boolean isMatchFor(String groupId, String artifactId)
	{
		return m_groupId.equals(groupId) && m_artifactId.equals(artifactId);
	}

	public void toSax(ContentHandler receiver, String namespace, String prefix, String localName) throws SAXException
	{
		AttributesImpl attrs = new AttributesImpl();
		addAttributes(attrs);
		String qName = Utils.makeQualifiedName(prefix, localName);
		receiver.startElement(namespace, localName, qName, attrs);
		emitElements(receiver, namespace, prefix);
		receiver.endElement(namespace, localName, qName);
	}

	void emitElements(ContentHandler receiver, String namespace, String prefix) throws SAXException
	{
	}

	void addAttributes(AttributesImpl attrs) throws SAXException
	{
		Utils.addAttribute(attrs, ATTR_GROUP_ID, m_groupId);
		Utils.addAttribute(attrs, ATTR_ARTIFACT_ID, m_artifactId);
	}

    @Override
    public String toString()
    {
        return m_groupId + ":" + m_artifactId;
    }

}
