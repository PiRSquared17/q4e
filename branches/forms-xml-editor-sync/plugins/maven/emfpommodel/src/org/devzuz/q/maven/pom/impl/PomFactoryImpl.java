/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.devzuz.q.maven.pom.impl;

import org.devzuz.q.maven.pom.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PomFactoryImpl extends EFactoryImpl implements PomFactory
{
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PomFactory init()
    {
        try
        {
            PomFactory thePomFactory = (PomFactory)EPackage.Registry.INSTANCE.getEFactory("http://maven.apache.org/POM/4.0.0"); 
            if (thePomFactory != null)
            {
                return thePomFactory;
            }
        }
        catch (Exception exception)
        {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new PomFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PomFactoryImpl()
    {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass)
    {
        switch (eClass.getClassifierID())
        {
            case PomPackage.ACTIVATION: return createActivation();
            case PomPackage.ACTIVATION_FILE: return createActivationFile();
            case PomPackage.ACTIVATION_OS: return createActivationOS();
            case PomPackage.ACTIVATION_PROPERTY: return createActivationProperty();
            case PomPackage.BUILD: return createBuild();
            case PomPackage.BUILD_BASE: return createBuildBase();
            case PomPackage.CI_MANAGEMENT: return createCiManagement();
            case PomPackage.CONFIGURATION_TYPE: return createConfigurationType();
            case PomPackage.CONFIGURATION_TYPE1: return createConfigurationType1();
            case PomPackage.CONFIGURATION_TYPE2: return createConfigurationType2();
            case PomPackage.CONFIGURATION_TYPE3: return createConfigurationType3();
            case PomPackage.CONFIGURATION_TYPE4: return createConfigurationType4();
            case PomPackage.CONTRIBUTOR: return createContributor();
            case PomPackage.CONTRIBUTORS_TYPE: return createContributorsType();
            case PomPackage.DEPENDENCIES_TYPE: return createDependenciesType();
            case PomPackage.DEPENDENCIES_TYPE1: return createDependenciesType1();
            case PomPackage.DEPENDENCIES_TYPE2: return createDependenciesType2();
            case PomPackage.DEPENDENCIES_TYPE3: return createDependenciesType3();
            case PomPackage.DEPENDENCY: return createDependency();
            case PomPackage.DEPENDENCY_MANAGEMENT: return createDependencyManagement();
            case PomPackage.DEPLOYMENT_REPOSITORY: return createDeploymentRepository();
            case PomPackage.DEVELOPER: return createDeveloper();
            case PomPackage.DEVELOPERS_TYPE: return createDevelopersType();
            case PomPackage.DISTRIBUTION_MANAGEMENT: return createDistributionManagement();
            case PomPackage.DOCUMENT_ROOT: return createDocumentRoot();
            case PomPackage.EXCLUDES_TYPE: return createExcludesType();
            case PomPackage.EXCLUSION: return createExclusion();
            case PomPackage.EXCLUSIONS_TYPE: return createExclusionsType();
            case PomPackage.EXECUTIONS_TYPE: return createExecutionsType();
            case PomPackage.EXTENSION: return createExtension();
            case PomPackage.EXTENSIONS_TYPE: return createExtensionsType();
            case PomPackage.FILTERS_TYPE: return createFiltersType();
            case PomPackage.FILTERS_TYPE1: return createFiltersType1();
            case PomPackage.GOALS_TYPE: return createGoalsType();
            case PomPackage.GOALS_TYPE1: return createGoalsType1();
            case PomPackage.INCLUDES_TYPE: return createIncludesType();
            case PomPackage.ISSUE_MANAGEMENT: return createIssueManagement();
            case PomPackage.LICENSE: return createLicense();
            case PomPackage.LICENSES_TYPE: return createLicensesType();
            case PomPackage.MAILING_LIST: return createMailingList();
            case PomPackage.MAILING_LISTS_TYPE: return createMailingListsType();
            case PomPackage.MODEL: return createModel();
            case PomPackage.MODULES_TYPE: return createModulesType();
            case PomPackage.MODULES_TYPE1: return createModulesType1();
            case PomPackage.NOTIFIER: return createNotifier();
            case PomPackage.NOTIFIERS_TYPE: return createNotifiersType();
            case PomPackage.ORGANIZATION: return createOrganization();
            case PomPackage.OTHER_ARCHIVES_TYPE: return createOtherArchivesType();
            case PomPackage.PARENT: return createParent();
            case PomPackage.PLUGIN: return createPlugin();
            case PomPackage.PLUGIN_EXECUTION: return createPluginExecution();
            case PomPackage.PLUGIN_MANAGEMENT: return createPluginManagement();
            case PomPackage.PLUGIN_REPOSITORIES_TYPE: return createPluginRepositoriesType();
            case PomPackage.PLUGIN_REPOSITORIES_TYPE1: return createPluginRepositoriesType1();
            case PomPackage.PLUGINS_TYPE: return createPluginsType();
            case PomPackage.PLUGINS_TYPE1: return createPluginsType1();
            case PomPackage.PLUGINS_TYPE2: return createPluginsType2();
            case PomPackage.PLUGINS_TYPE3: return createPluginsType3();
            case PomPackage.PREREQUISITES: return createPrerequisites();
            case PomPackage.PROFILE: return createProfile();
            case PomPackage.PROFILES_TYPE: return createProfilesType();
            case PomPackage.PROPERTIES_TYPE: return createPropertiesType();
            case PomPackage.PROPERTIES_TYPE1: return createPropertiesType1();
            case PomPackage.PROPERTIES_TYPE2: return createPropertiesType2();
            case PomPackage.PROPERTIES_TYPE3: return createPropertiesType3();
            case PomPackage.RELOCATION: return createRelocation();
            case PomPackage.REPORTING: return createReporting();
            case PomPackage.REPORT_PLUGIN: return createReportPlugin();
            case PomPackage.REPORT_SET: return createReportSet();
            case PomPackage.REPORT_SETS_TYPE: return createReportSetsType();
            case PomPackage.REPORTS_TYPE: return createReportsType();
            case PomPackage.REPORTS_TYPE1: return createReportsType1();
            case PomPackage.REPORTS_TYPE2: return createReportsType2();
            case PomPackage.REPOSITORIES_TYPE: return createRepositoriesType();
            case PomPackage.REPOSITORIES_TYPE1: return createRepositoriesType1();
            case PomPackage.REPOSITORY: return createRepository();
            case PomPackage.REPOSITORY_POLICY: return createRepositoryPolicy();
            case PomPackage.RESOURCE: return createResource();
            case PomPackage.RESOURCES_TYPE: return createResourcesType();
            case PomPackage.RESOURCES_TYPE1: return createResourcesType1();
            case PomPackage.ROLES_TYPE: return createRolesType();
            case PomPackage.ROLES_TYPE1: return createRolesType1();
            case PomPackage.SCM: return createScm();
            case PomPackage.SITE: return createSite();
            case PomPackage.TEST_RESOURCES_TYPE: return createTestResourcesType();
            case PomPackage.TEST_RESOURCES_TYPE1: return createTestResourcesType1();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Activation createActivation()
    {
        ActivationImpl activation = new ActivationImpl();
        return activation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ActivationFile createActivationFile()
    {
        ActivationFileImpl activationFile = new ActivationFileImpl();
        return activationFile;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ActivationOS createActivationOS()
    {
        ActivationOSImpl activationOS = new ActivationOSImpl();
        return activationOS;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ActivationProperty createActivationProperty()
    {
        ActivationPropertyImpl activationProperty = new ActivationPropertyImpl();
        return activationProperty;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Build createBuild()
    {
        BuildImpl build = new BuildImpl();
        return build;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public BuildBase createBuildBase()
    {
        BuildBaseImpl buildBase = new BuildBaseImpl();
        return buildBase;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public CiManagement createCiManagement()
    {
        CiManagementImpl ciManagement = new CiManagementImpl();
        return ciManagement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ConfigurationType createConfigurationType()
    {
        ConfigurationTypeImpl configurationType = new ConfigurationTypeImpl();
        return configurationType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ConfigurationType1 createConfigurationType1()
    {
        ConfigurationType1Impl configurationType1 = new ConfigurationType1Impl();
        return configurationType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ConfigurationType2 createConfigurationType2()
    {
        ConfigurationType2Impl configurationType2 = new ConfigurationType2Impl();
        return configurationType2;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ConfigurationType3 createConfigurationType3()
    {
        ConfigurationType3Impl configurationType3 = new ConfigurationType3Impl();
        return configurationType3;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ConfigurationType4 createConfigurationType4()
    {
        ConfigurationType4Impl configurationType4 = new ConfigurationType4Impl();
        return configurationType4;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Contributor createContributor()
    {
        ContributorImpl contributor = new ContributorImpl();
        return contributor;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ContributorsType createContributorsType()
    {
        ContributorsTypeImpl contributorsType = new ContributorsTypeImpl();
        return contributorsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DependenciesType createDependenciesType()
    {
        DependenciesTypeImpl dependenciesType = new DependenciesTypeImpl();
        return dependenciesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DependenciesType1 createDependenciesType1()
    {
        DependenciesType1Impl dependenciesType1 = new DependenciesType1Impl();
        return dependenciesType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DependenciesType2 createDependenciesType2()
    {
        DependenciesType2Impl dependenciesType2 = new DependenciesType2Impl();
        return dependenciesType2;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DependenciesType3 createDependenciesType3()
    {
        DependenciesType3Impl dependenciesType3 = new DependenciesType3Impl();
        return dependenciesType3;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Dependency createDependency()
    {
        DependencyImpl dependency = new DependencyImpl();
        return dependency;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DependencyManagement createDependencyManagement()
    {
        DependencyManagementImpl dependencyManagement = new DependencyManagementImpl();
        return dependencyManagement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DeploymentRepository createDeploymentRepository()
    {
        DeploymentRepositoryImpl deploymentRepository = new DeploymentRepositoryImpl();
        return deploymentRepository;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Developer createDeveloper()
    {
        DeveloperImpl developer = new DeveloperImpl();
        return developer;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DevelopersType createDevelopersType()
    {
        DevelopersTypeImpl developersType = new DevelopersTypeImpl();
        return developersType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DistributionManagement createDistributionManagement()
    {
        DistributionManagementImpl distributionManagement = new DistributionManagementImpl();
        return distributionManagement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DocumentRoot createDocumentRoot()
    {
        DocumentRootImpl documentRoot = new DocumentRootImpl();
        return documentRoot;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExcludesType createExcludesType()
    {
        ExcludesTypeImpl excludesType = new ExcludesTypeImpl();
        return excludesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Exclusion createExclusion()
    {
        ExclusionImpl exclusion = new ExclusionImpl();
        return exclusion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExclusionsType createExclusionsType()
    {
        ExclusionsTypeImpl exclusionsType = new ExclusionsTypeImpl();
        return exclusionsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExecutionsType createExecutionsType()
    {
        ExecutionsTypeImpl executionsType = new ExecutionsTypeImpl();
        return executionsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Extension createExtension()
    {
        ExtensionImpl extension = new ExtensionImpl();
        return extension;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ExtensionsType createExtensionsType()
    {
        ExtensionsTypeImpl extensionsType = new ExtensionsTypeImpl();
        return extensionsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FiltersType createFiltersType()
    {
        FiltersTypeImpl filtersType = new FiltersTypeImpl();
        return filtersType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FiltersType1 createFiltersType1()
    {
        FiltersType1Impl filtersType1 = new FiltersType1Impl();
        return filtersType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GoalsType createGoalsType()
    {
        GoalsTypeImpl goalsType = new GoalsTypeImpl();
        return goalsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public GoalsType1 createGoalsType1()
    {
        GoalsType1Impl goalsType1 = new GoalsType1Impl();
        return goalsType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public IncludesType createIncludesType()
    {
        IncludesTypeImpl includesType = new IncludesTypeImpl();
        return includesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public IssueManagement createIssueManagement()
    {
        IssueManagementImpl issueManagement = new IssueManagementImpl();
        return issueManagement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public License createLicense()
    {
        LicenseImpl license = new LicenseImpl();
        return license;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LicensesType createLicensesType()
    {
        LicensesTypeImpl licensesType = new LicensesTypeImpl();
        return licensesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MailingList createMailingList()
    {
        MailingListImpl mailingList = new MailingListImpl();
        return mailingList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public MailingListsType createMailingListsType()
    {
        MailingListsTypeImpl mailingListsType = new MailingListsTypeImpl();
        return mailingListsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Model createModel()
    {
        ModelImpl model = new ModelImpl();
        return model;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModulesType createModulesType()
    {
        ModulesTypeImpl modulesType = new ModulesTypeImpl();
        return modulesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ModulesType1 createModulesType1()
    {
        ModulesType1Impl modulesType1 = new ModulesType1Impl();
        return modulesType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Notifier createNotifier()
    {
        NotifierImpl notifier = new NotifierImpl();
        return notifier;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotifiersType createNotifiersType()
    {
        NotifiersTypeImpl notifiersType = new NotifiersTypeImpl();
        return notifiersType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Organization createOrganization()
    {
        OrganizationImpl organization = new OrganizationImpl();
        return organization;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OtherArchivesType createOtherArchivesType()
    {
        OtherArchivesTypeImpl otherArchivesType = new OtherArchivesTypeImpl();
        return otherArchivesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Parent createParent()
    {
        ParentImpl parent = new ParentImpl();
        return parent;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Plugin createPlugin()
    {
        PluginImpl plugin = new PluginImpl();
        return plugin;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginExecution createPluginExecution()
    {
        PluginExecutionImpl pluginExecution = new PluginExecutionImpl();
        return pluginExecution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginManagement createPluginManagement()
    {
        PluginManagementImpl pluginManagement = new PluginManagementImpl();
        return pluginManagement;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginRepositoriesType createPluginRepositoriesType()
    {
        PluginRepositoriesTypeImpl pluginRepositoriesType = new PluginRepositoriesTypeImpl();
        return pluginRepositoriesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginRepositoriesType1 createPluginRepositoriesType1()
    {
        PluginRepositoriesType1Impl pluginRepositoriesType1 = new PluginRepositoriesType1Impl();
        return pluginRepositoriesType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginsType createPluginsType()
    {
        PluginsTypeImpl pluginsType = new PluginsTypeImpl();
        return pluginsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginsType1 createPluginsType1()
    {
        PluginsType1Impl pluginsType1 = new PluginsType1Impl();
        return pluginsType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginsType2 createPluginsType2()
    {
        PluginsType2Impl pluginsType2 = new PluginsType2Impl();
        return pluginsType2;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PluginsType3 createPluginsType3()
    {
        PluginsType3Impl pluginsType3 = new PluginsType3Impl();
        return pluginsType3;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Prerequisites createPrerequisites()
    {
        PrerequisitesImpl prerequisites = new PrerequisitesImpl();
        return prerequisites;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Profile createProfile()
    {
        ProfileImpl profile = new ProfileImpl();
        return profile;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ProfilesType createProfilesType()
    {
        ProfilesTypeImpl profilesType = new ProfilesTypeImpl();
        return profilesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertiesType createPropertiesType()
    {
        PropertiesTypeImpl propertiesType = new PropertiesTypeImpl();
        return propertiesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertiesType1 createPropertiesType1()
    {
        PropertiesType1Impl propertiesType1 = new PropertiesType1Impl();
        return propertiesType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertiesType2 createPropertiesType2()
    {
        PropertiesType2Impl propertiesType2 = new PropertiesType2Impl();
        return propertiesType2;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PropertiesType3 createPropertiesType3()
    {
        PropertiesType3Impl propertiesType3 = new PropertiesType3Impl();
        return propertiesType3;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Relocation createRelocation()
    {
        RelocationImpl relocation = new RelocationImpl();
        return relocation;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Reporting createReporting()
    {
        ReportingImpl reporting = new ReportingImpl();
        return reporting;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReportPlugin createReportPlugin()
    {
        ReportPluginImpl reportPlugin = new ReportPluginImpl();
        return reportPlugin;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReportSet createReportSet()
    {
        ReportSetImpl reportSet = new ReportSetImpl();
        return reportSet;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReportSetsType createReportSetsType()
    {
        ReportSetsTypeImpl reportSetsType = new ReportSetsTypeImpl();
        return reportSetsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReportsType createReportsType()
    {
        ReportsTypeImpl reportsType = new ReportsTypeImpl();
        return reportsType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReportsType1 createReportsType1()
    {
        ReportsType1Impl reportsType1 = new ReportsType1Impl();
        return reportsType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ReportsType2 createReportsType2()
    {
        ReportsType2Impl reportsType2 = new ReportsType2Impl();
        return reportsType2;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RepositoriesType createRepositoriesType()
    {
        RepositoriesTypeImpl repositoriesType = new RepositoriesTypeImpl();
        return repositoriesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RepositoriesType1 createRepositoriesType1()
    {
        RepositoriesType1Impl repositoriesType1 = new RepositoriesType1Impl();
        return repositoriesType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Repository createRepository()
    {
        RepositoryImpl repository = new RepositoryImpl();
        return repository;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RepositoryPolicy createRepositoryPolicy()
    {
        RepositoryPolicyImpl repositoryPolicy = new RepositoryPolicyImpl();
        return repositoryPolicy;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Resource createResource()
    {
        ResourceImpl resource = new ResourceImpl();
        return resource;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourcesType createResourcesType()
    {
        ResourcesTypeImpl resourcesType = new ResourcesTypeImpl();
        return resourcesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ResourcesType1 createResourcesType1()
    {
        ResourcesType1Impl resourcesType1 = new ResourcesType1Impl();
        return resourcesType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RolesType createRolesType()
    {
        RolesTypeImpl rolesType = new RolesTypeImpl();
        return rolesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public RolesType1 createRolesType1()
    {
        RolesType1Impl rolesType1 = new RolesType1Impl();
        return rolesType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Scm createScm()
    {
        ScmImpl scm = new ScmImpl();
        return scm;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Site createSite()
    {
        SiteImpl site = new SiteImpl();
        return site;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TestResourcesType createTestResourcesType()
    {
        TestResourcesTypeImpl testResourcesType = new TestResourcesTypeImpl();
        return testResourcesType;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TestResourcesType1 createTestResourcesType1()
    {
        TestResourcesType1Impl testResourcesType1 = new TestResourcesType1Impl();
        return testResourcesType1;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PomPackage getPomPackage()
    {
        return (PomPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static PomPackage getPackage()
    {
        return PomPackage.eINSTANCE;
    }

} //PomFactoryImpl
