
package com.ai.oimspmlclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ai.oimspmlclient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DeleteRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "deleteRequest");
    private final static QName _ValidatePasswordRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:password", "validatePasswordRequest");
    private final static QName _SuggestUsernameRequest_QNAME = new QName("http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", "suggestUsernameRequest");
    private final static QName _IsActive_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:suspend", "isActive");
    private final static QName _SuspendResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:suspend", "suspendResponse");
    private final static QName _SetPasswordRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:password", "setPasswordRequest");
    private final static QName _ValidatePasswordResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:password", "validatePasswordResponse");
    private final static QName _ValidateUsernameResponse_QNAME = new QName("http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", "validateUsernameResponse");
    private final static QName _RoleWithRelations_QNAME = new QName("http://xmlns.oracle.com/idm/identity/PSO", "roleWithRelations");
    private final static QName _ResetPasswordResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:password", "resetPasswordResponse");
    private final static QName _IdentityWithRelations_QNAME = new QName("http://xmlns.oracle.com/idm/identity/PSO", "identityWithRelations");
    private final static QName _Member_QNAME = new QName("http://xmlns.oracle.com/idm/identity/PSO", "member");
    private final static QName _ValidateUsernameRequest_QNAME = new QName("http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", "validateUsernameRequest");
    private final static QName _ResumeRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:suspend", "resumeRequest");
    private final static QName _LookupRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "lookupRequest");
    private final static QName _LookupUsernamePolicyRequest_QNAME = new QName("http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", "lookupUsernamePolicyRequest");
    private final static QName _ListTargetsResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "listTargetsResponse");
    private final static QName _Reference_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:reference", "reference");
    private final static QName _OperationData_QNAME = new QName("http://xmlns.oracle.com/idm/identity/OperationData", "operationData");
    private final static QName _ServiceHeader_QNAME = new QName("urn:names:spml:ws:header", "ServiceHeader");
    private final static QName _LookupUsernamePolicyResponse_QNAME = new QName("http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", "lookupUsernamePolicyResponse");
    private final static QName _ExpirePasswordRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:password", "expirePasswordRequest");
    private final static QName _CancelResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:async", "cancelResponse");
    private final static QName _AddResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "addResponse");
    private final static QName _Select_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "select");
    private final static QName _SetPasswordResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:password", "setPasswordResponse");
    private final static QName _ResumeResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:suspend", "resumeResponse");
    private final static QName _ListTargetsRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "listTargetsRequest");
    private final static QName _StatusRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:async", "statusRequest");
    private final static QName _ActiveResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:suspend", "activeResponse");
    private final static QName _HasReference_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:reference", "hasReference");
    private final static QName _AddRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "addRequest");
    private final static QName _ReferenceDefinition_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:reference", "referenceDefinition");
    private final static QName _DeleteResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "deleteResponse");
    private final static QName _Role_QNAME = new QName("http://xmlns.oracle.com/idm/identity/PSO", "role");
    private final static QName _SuspendRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:suspend", "suspendRequest");
    private final static QName _ActiveRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:suspend", "activeRequest");
    private final static QName _CancelRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:async", "cancelRequest");
    private final static QName _ModifyRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "modifyRequest");
    private final static QName _LookupResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "lookupResponse");
    private final static QName _StatusResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:async", "statusResponse");
    private final static QName _ModifyResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0", "modifyResponse");
    private final static QName _Identity_QNAME = new QName("http://xmlns.oracle.com/idm/identity/PSO", "identity");
    private final static QName _ResetPasswordRequest_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:password", "resetPasswordRequest");
    private final static QName _ExpirePasswordResponse_QNAME = new QName("urn:oasis:names:tc:SPML:2:0:password", "expirePasswordResponse");
    private final static QName _SuggestUsernameResponse_QNAME = new QName("http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", "suggestUsernameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ai.oimspmlclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NamespacePrefixMappingType }
     * 
     */
    public NamespacePrefixMappingType createNamespacePrefixMappingType() {
        return new NamespacePrefixMappingType();
    }

    /**
     * Create an instance of {@link LocalizedString }
     * 
     */
    public LocalizedString createLocalizedString() {
        return new LocalizedString();
    }

    /**
     * Create an instance of {@link ListTargetsRequestType }
     * 
     */
    public ListTargetsRequestType createListTargetsRequestType() {
        return new ListTargetsRequestType();
    }

    /**
     * Create an instance of {@link MultiValuedBinary }
     * 
     */
    public MultiValuedBinary createMultiValuedBinary() {
        return new MultiValuedBinary();
    }

    /**
     * Create an instance of {@link DeleteRequestType }
     * 
     */
    public DeleteRequestType createDeleteRequestType() {
        return new DeleteRequestType();
    }

    /**
     * Create an instance of {@link AddRequestType }
     * 
     */
    public AddRequestType createAddRequestType() {
        return new AddRequestType();
    }

    /**
     * Create an instance of {@link ModifyRequestType }
     * 
     */
    public ModifyRequestType createModifyRequestType() {
        return new ModifyRequestType();
    }

    /**
     * Create an instance of {@link Member }
     * 
     */
    public Member createMember() {
        return new Member();
    }

    /**
     * Create an instance of {@link CapabilitiesListType }
     * 
     */
    public CapabilitiesListType createCapabilitiesListType() {
        return new CapabilitiesListType();
    }

    /**
     * Create an instance of {@link SuggestUsernameRequestType }
     * 
     */
    public SuggestUsernameRequestType createSuggestUsernameRequestType() {
        return new SuggestUsernameRequestType();
    }

    /**
     * Create an instance of {@link TelephoneNumbers }
     * 
     */
    public TelephoneNumbers createTelephoneNumbers() {
        return new TelephoneNumbers();
    }

    /**
     * Create an instance of {@link LookupUsernamePolicyRequestType }
     * 
     */
    public LookupUsernamePolicyRequestType createLookupUsernamePolicyRequestType() {
        return new LookupUsernamePolicyRequestType();
    }

    /**
     * Create an instance of {@link ActiveRequestType }
     * 
     */
    public ActiveRequestType createActiveRequestType() {
        return new ActiveRequestType();
    }

    /**
     * Create an instance of {@link ValidatePasswordRequestType }
     * 
     */
    public ValidatePasswordRequestType createValidatePasswordRequestType() {
        return new ValidatePasswordRequestType();
    }

    /**
     * Create an instance of {@link ServiceHeaderType }
     * 
     */
    public ServiceHeaderType createServiceHeaderType() {
        return new ServiceHeaderType();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link ResetPasswordResponseType }
     * 
     */
    public ResetPasswordResponseType createResetPasswordResponseType() {
        return new ResetPasswordResponseType();
    }

    /**
     * Create an instance of {@link PSOType }
     * 
     */
    public PSOType createPSOType() {
        return new PSOType();
    }

    /**
     * Create an instance of {@link Identity }
     * 
     */
    public Identity createIdentity() {
        return new Identity();
    }

    /**
     * Create an instance of {@link ProvisioningObjectType }
     * 
     */
    public ProvisioningObjectType createProvisioningObjectType() {
        return new ProvisioningObjectType();
    }

    /**
     * Create an instance of {@link LocalizedStrings }
     * 
     */
    public LocalizedStrings createLocalizedStrings() {
        return new LocalizedStrings();
    }

    /**
     * Create an instance of {@link ExtensibleType }
     * 
     */
    public ExtensibleType createExtensibleType() {
        return new ExtensibleType();
    }

    /**
     * Create an instance of {@link CancelRequestType }
     * 
     */
    public CancelRequestType createCancelRequestType() {
        return new CancelRequestType();
    }

    /**
     * Create an instance of {@link ValidateUsernameResponseType }
     * 
     */
    public ValidateUsernameResponseType createValidateUsernameResponseType() {
        return new ValidateUsernameResponseType();
    }

    /**
     * Create an instance of {@link Role }
     * 
     */
    public Role createRole() {
        return new Role();
    }

    /**
     * Create an instance of {@link ResumeRequestType }
     * 
     */
    public ResumeRequestType createResumeRequestType() {
        return new ResumeRequestType();
    }

    /**
     * Create an instance of {@link SuspendRequestType }
     * 
     */
    public SuspendRequestType createSuspendRequestType() {
        return new SuspendRequestType();
    }

    /**
     * Create an instance of {@link LookupUsernamePolicyResponseType }
     * 
     */
    public LookupUsernamePolicyResponseType createLookupUsernamePolicyResponseType() {
        return new LookupUsernamePolicyResponseType();
    }

    /**
     * Create an instance of {@link IsActiveType }
     * 
     */
    public IsActiveType createIsActiveType() {
        return new IsActiveType();
    }

    /**
     * Create an instance of {@link RoleWithRelations }
     * 
     */
    public RoleWithRelations createRoleWithRelations() {
        return new RoleWithRelations();
    }

    /**
     * Create an instance of {@link CapabilityDataType }
     * 
     */
    public CapabilityDataType createCapabilityDataType() {
        return new CapabilityDataType();
    }

    /**
     * Create an instance of {@link ValidateUsernameRequestType }
     * 
     */
    public ValidateUsernameRequestType createValidateUsernameRequestType() {
        return new ValidateUsernameRequestType();
    }

    /**
     * Create an instance of {@link LocalizedSingleValuedString }
     * 
     */
    public LocalizedSingleValuedString createLocalizedSingleValuedString() {
        return new LocalizedSingleValuedString();
    }

    /**
     * Create an instance of {@link MultiValuedString }
     * 
     */
    public MultiValuedString createMultiValuedString() {
        return new MultiValuedString();
    }

    /**
     * Create an instance of {@link SelectionType }
     * 
     */
    public SelectionType createSelectionType() {
        return new SelectionType();
    }

    /**
     * Create an instance of {@link ResponseType }
     * 
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link SetPasswordRequestType }
     * 
     */
    public SetPasswordRequestType createSetPasswordRequestType() {
        return new SetPasswordRequestType();
    }

    /**
     * Create an instance of {@link QueryClauseType }
     * 
     */
    public QueryClauseType createQueryClauseType() {
        return new QueryClauseType();
    }

    /**
     * Create an instance of {@link ReferenceDefinitionType }
     * 
     */
    public ReferenceDefinitionType createReferenceDefinitionType() {
        return new ReferenceDefinitionType();
    }

    /**
     * Create an instance of {@link ValidatePasswordResponseType }
     * 
     */
    public ValidatePasswordResponseType createValidatePasswordResponseType() {
        return new ValidatePasswordResponseType();
    }

    /**
     * Create an instance of {@link AddResponseType }
     * 
     */
    public AddResponseType createAddResponseType() {
        return new AddResponseType();
    }

    /**
     * Create an instance of {@link ModifyResponseType }
     * 
     */
    public ModifyResponseType createModifyResponseType() {
        return new ModifyResponseType();
    }

    /**
     * Create an instance of {@link LookupRequestType }
     * 
     */
    public LookupRequestType createLookupRequestType() {
        return new LookupRequestType();
    }

    /**
     * Create an instance of {@link DsmlAttr }
     * 
     */
    public DsmlAttr createDsmlAttr() {
        return new DsmlAttr();
    }

    /**
     * Create an instance of {@link PSOIdentifierType }
     * 
     */
    public PSOIdentifierType createPSOIdentifierType() {
        return new PSOIdentifierType();
    }

    /**
     * Create an instance of {@link CapabilityType }
     * 
     */
    public CapabilityType createCapabilityType() {
        return new CapabilityType();
    }

    /**
     * Create an instance of {@link LocalizedMultiValuedString }
     * 
     */
    public LocalizedMultiValuedString createLocalizedMultiValuedString() {
        return new LocalizedMultiValuedString();
    }

    /**
     * Create an instance of {@link StatusResponseType }
     * 
     */
    public StatusResponseType createStatusResponseType() {
        return new StatusResponseType();
    }

    /**
     * Create an instance of {@link IdentifierType }
     * 
     */
    public IdentifierType createIdentifierType() {
        return new IdentifierType();
    }

    /**
     * Create an instance of {@link SuggestUsernameResponseType }
     * 
     */
    public SuggestUsernameResponseType createSuggestUsernameResponseType() {
        return new SuggestUsernameResponseType();
    }

    /**
     * Create an instance of {@link TargetType }
     * 
     */
    public TargetType createTargetType() {
        return new TargetType();
    }

    /**
     * Create an instance of {@link LookupResponseType }
     * 
     */
    public LookupResponseType createLookupResponseType() {
        return new LookupResponseType();
    }

    /**
     * Create an instance of {@link ListTargetsResponseType }
     * 
     */
    public ListTargetsResponseType createListTargetsResponseType() {
        return new ListTargetsResponseType();
    }

    /**
     * Create an instance of {@link ResetPasswordRequestType }
     * 
     */
    public ResetPasswordRequestType createResetPasswordRequestType() {
        return new ResetPasswordRequestType();
    }

    /**
     * Create an instance of {@link StatusRequestType }
     * 
     */
    public StatusRequestType createStatusRequestType() {
        return new StatusRequestType();
    }

    /**
     * Create an instance of {@link ModificationType }
     * 
     */
    public ModificationType createModificationType() {
        return new ModificationType();
    }

    /**
     * Create an instance of {@link IdentityWithRelations }
     * 
     */
    public IdentityWithRelations createIdentityWithRelations() {
        return new IdentityWithRelations();
    }

    /**
     * Create an instance of {@link ReferenceType }
     * 
     */
    public ReferenceType createReferenceType() {
        return new ReferenceType();
    }

    /**
     * Create an instance of {@link CancelResponseType }
     * 
     */
    public CancelResponseType createCancelResponseType() {
        return new CancelResponseType();
    }

    /**
     * Create an instance of {@link SchemaType }
     * 
     */
    public SchemaType createSchemaType() {
        return new SchemaType();
    }

    /**
     * Create an instance of {@link ActiveResponseType }
     * 
     */
    public ActiveResponseType createActiveResponseType() {
        return new ActiveResponseType();
    }

    /**
     * Create an instance of {@link HasReferenceType }
     * 
     */
    public HasReferenceType createHasReferenceType() {
        return new HasReferenceType();
    }

    /**
     * Create an instance of {@link OperationDataType }
     * 
     */
    public OperationDataType createOperationDataType() {
        return new OperationDataType();
    }

    /**
     * Create an instance of {@link SchemaEntityRefType }
     * 
     */
    public SchemaEntityRefType createSchemaEntityRefType() {
        return new SchemaEntityRefType();
    }

    /**
     * Create an instance of {@link ExpirePasswordRequestType }
     * 
     */
    public ExpirePasswordRequestType createExpirePasswordRequestType() {
        return new ExpirePasswordRequestType();
    }

    /**
     * Create an instance of {@link UnboundedAttributes }
     * 
     */
    public UnboundedAttributes createUnboundedAttributes() {
        return new UnboundedAttributes();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "deleteRequest")
    public JAXBElement<DeleteRequestType> createDeleteRequest(DeleteRequestType value) {
        return new JAXBElement<DeleteRequestType>(_DeleteRequest_QNAME, DeleteRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidatePasswordRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:password", name = "validatePasswordRequest")
    public JAXBElement<ValidatePasswordRequestType> createValidatePasswordRequest(ValidatePasswordRequestType value) {
        return new JAXBElement<ValidatePasswordRequestType>(_ValidatePasswordRequest_QNAME, ValidatePasswordRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SuggestUsernameRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", name = "suggestUsernameRequest")
    public JAXBElement<SuggestUsernameRequestType> createSuggestUsernameRequest(SuggestUsernameRequestType value) {
        return new JAXBElement<SuggestUsernameRequestType>(_SuggestUsernameRequest_QNAME, SuggestUsernameRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsActiveType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:suspend", name = "isActive")
    public JAXBElement<IsActiveType> createIsActive(IsActiveType value) {
        return new JAXBElement<IsActiveType>(_IsActive_QNAME, IsActiveType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:suspend", name = "suspendResponse")
    public JAXBElement<ResponseType> createSuspendResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_SuspendResponse_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetPasswordRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:password", name = "setPasswordRequest")
    public JAXBElement<SetPasswordRequestType> createSetPasswordRequest(SetPasswordRequestType value) {
        return new JAXBElement<SetPasswordRequestType>(_SetPasswordRequest_QNAME, SetPasswordRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidatePasswordResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:password", name = "validatePasswordResponse")
    public JAXBElement<ValidatePasswordResponseType> createValidatePasswordResponse(ValidatePasswordResponseType value) {
        return new JAXBElement<ValidatePasswordResponseType>(_ValidatePasswordResponse_QNAME, ValidatePasswordResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateUsernameResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", name = "validateUsernameResponse")
    public JAXBElement<ValidateUsernameResponseType> createValidateUsernameResponse(ValidateUsernameResponseType value) {
        return new JAXBElement<ValidateUsernameResponseType>(_ValidateUsernameResponse_QNAME, ValidateUsernameResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RoleWithRelations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/PSO", name = "roleWithRelations")
    public JAXBElement<RoleWithRelations> createRoleWithRelations(RoleWithRelations value) {
        return new JAXBElement<RoleWithRelations>(_RoleWithRelations_QNAME, RoleWithRelations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetPasswordResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:password", name = "resetPasswordResponse")
    public JAXBElement<ResetPasswordResponseType> createResetPasswordResponse(ResetPasswordResponseType value) {
        return new JAXBElement<ResetPasswordResponseType>(_ResetPasswordResponse_QNAME, ResetPasswordResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentityWithRelations }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/PSO", name = "identityWithRelations")
    public JAXBElement<IdentityWithRelations> createIdentityWithRelations(IdentityWithRelations value) {
        return new JAXBElement<IdentityWithRelations>(_IdentityWithRelations_QNAME, IdentityWithRelations.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Member }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/PSO", name = "member")
    public JAXBElement<Member> createMember(Member value) {
        return new JAXBElement<Member>(_Member_QNAME, Member.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateUsernameRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", name = "validateUsernameRequest")
    public JAXBElement<ValidateUsernameRequestType> createValidateUsernameRequest(ValidateUsernameRequestType value) {
        return new JAXBElement<ValidateUsernameRequestType>(_ValidateUsernameRequest_QNAME, ValidateUsernameRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResumeRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:suspend", name = "resumeRequest")
    public JAXBElement<ResumeRequestType> createResumeRequest(ResumeRequestType value) {
        return new JAXBElement<ResumeRequestType>(_ResumeRequest_QNAME, ResumeRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "lookupRequest")
    public JAXBElement<LookupRequestType> createLookupRequest(LookupRequestType value) {
        return new JAXBElement<LookupRequestType>(_LookupRequest_QNAME, LookupRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupUsernamePolicyRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", name = "lookupUsernamePolicyRequest")
    public JAXBElement<LookupUsernamePolicyRequestType> createLookupUsernamePolicyRequest(LookupUsernamePolicyRequestType value) {
        return new JAXBElement<LookupUsernamePolicyRequestType>(_LookupUsernamePolicyRequest_QNAME, LookupUsernamePolicyRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListTargetsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "listTargetsResponse")
    public JAXBElement<ListTargetsResponseType> createListTargetsResponse(ListTargetsResponseType value) {
        return new JAXBElement<ListTargetsResponseType>(_ListTargetsResponse_QNAME, ListTargetsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:reference", name = "reference")
    public JAXBElement<ReferenceType> createReference(ReferenceType value) {
        return new JAXBElement<ReferenceType>(_Reference_QNAME, ReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationDataType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/OperationData", name = "operationData")
    public JAXBElement<OperationDataType> createOperationData(OperationDataType value) {
        return new JAXBElement<OperationDataType>(_OperationData_QNAME, OperationDataType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceHeaderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:names:spml:ws:header", name = "ServiceHeader")
    public JAXBElement<ServiceHeaderType> createServiceHeader(ServiceHeaderType value) {
        return new JAXBElement<ServiceHeaderType>(_ServiceHeader_QNAME, ServiceHeaderType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupUsernamePolicyResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", name = "lookupUsernamePolicyResponse")
    public JAXBElement<LookupUsernamePolicyResponseType> createLookupUsernamePolicyResponse(LookupUsernamePolicyResponseType value) {
        return new JAXBElement<LookupUsernamePolicyResponseType>(_LookupUsernamePolicyResponse_QNAME, LookupUsernamePolicyResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExpirePasswordRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:password", name = "expirePasswordRequest")
    public JAXBElement<ExpirePasswordRequestType> createExpirePasswordRequest(ExpirePasswordRequestType value) {
        return new JAXBElement<ExpirePasswordRequestType>(_ExpirePasswordRequest_QNAME, ExpirePasswordRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:async", name = "cancelResponse")
    public JAXBElement<CancelResponseType> createCancelResponse(CancelResponseType value) {
        return new JAXBElement<CancelResponseType>(_CancelResponse_QNAME, CancelResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "addResponse")
    public JAXBElement<AddResponseType> createAddResponse(AddResponseType value) {
        return new JAXBElement<AddResponseType>(_AddResponse_QNAME, AddResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "select")
    public JAXBElement<SelectionType> createSelect(SelectionType value) {
        return new JAXBElement<SelectionType>(_Select_QNAME, SelectionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:password", name = "setPasswordResponse")
    public JAXBElement<ResponseType> createSetPasswordResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_SetPasswordResponse_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:suspend", name = "resumeResponse")
    public JAXBElement<ResponseType> createResumeResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_ResumeResponse_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListTargetsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "listTargetsRequest")
    public JAXBElement<ListTargetsRequestType> createListTargetsRequest(ListTargetsRequestType value) {
        return new JAXBElement<ListTargetsRequestType>(_ListTargetsRequest_QNAME, ListTargetsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:async", name = "statusRequest")
    public JAXBElement<StatusRequestType> createStatusRequest(StatusRequestType value) {
        return new JAXBElement<StatusRequestType>(_StatusRequest_QNAME, StatusRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActiveResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:suspend", name = "activeResponse")
    public JAXBElement<ActiveResponseType> createActiveResponse(ActiveResponseType value) {
        return new JAXBElement<ActiveResponseType>(_ActiveResponse_QNAME, ActiveResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasReferenceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:reference", name = "hasReference")
    public JAXBElement<HasReferenceType> createHasReference(HasReferenceType value) {
        return new JAXBElement<HasReferenceType>(_HasReference_QNAME, HasReferenceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "addRequest")
    public JAXBElement<AddRequestType> createAddRequest(AddRequestType value) {
        return new JAXBElement<AddRequestType>(_AddRequest_QNAME, AddRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReferenceDefinitionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:reference", name = "referenceDefinition")
    public JAXBElement<ReferenceDefinitionType> createReferenceDefinition(ReferenceDefinitionType value) {
        return new JAXBElement<ReferenceDefinitionType>(_ReferenceDefinition_QNAME, ReferenceDefinitionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "deleteResponse")
    public JAXBElement<ResponseType> createDeleteResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_DeleteResponse_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Role }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/PSO", name = "role")
    public JAXBElement<Role> createRole(Role value) {
        return new JAXBElement<Role>(_Role_QNAME, Role.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SuspendRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:suspend", name = "suspendRequest")
    public JAXBElement<SuspendRequestType> createSuspendRequest(SuspendRequestType value) {
        return new JAXBElement<SuspendRequestType>(_SuspendRequest_QNAME, SuspendRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ActiveRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:suspend", name = "activeRequest")
    public JAXBElement<ActiveRequestType> createActiveRequest(ActiveRequestType value) {
        return new JAXBElement<ActiveRequestType>(_ActiveRequest_QNAME, ActiveRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CancelRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:async", name = "cancelRequest")
    public JAXBElement<CancelRequestType> createCancelRequest(CancelRequestType value) {
        return new JAXBElement<CancelRequestType>(_CancelRequest_QNAME, CancelRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "modifyRequest")
    public JAXBElement<ModifyRequestType> createModifyRequest(ModifyRequestType value) {
        return new JAXBElement<ModifyRequestType>(_ModifyRequest_QNAME, ModifyRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LookupResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "lookupResponse")
    public JAXBElement<LookupResponseType> createLookupResponse(LookupResponseType value) {
        return new JAXBElement<LookupResponseType>(_LookupResponse_QNAME, LookupResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:async", name = "statusResponse")
    public JAXBElement<StatusResponseType> createStatusResponse(StatusResponseType value) {
        return new JAXBElement<StatusResponseType>(_StatusResponse_QNAME, StatusResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0", name = "modifyResponse")
    public JAXBElement<ModifyResponseType> createModifyResponse(ModifyResponseType value) {
        return new JAXBElement<ModifyResponseType>(_ModifyResponse_QNAME, ModifyResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Identity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/PSO", name = "identity")
    public JAXBElement<Identity> createIdentity(Identity value) {
        return new JAXBElement<Identity>(_Identity_QNAME, Identity.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetPasswordRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:password", name = "resetPasswordRequest")
    public JAXBElement<ResetPasswordRequestType> createResetPasswordRequest(ResetPasswordRequestType value) {
        return new JAXBElement<ResetPasswordRequestType>(_ResetPasswordRequest_QNAME, ResetPasswordRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "urn:oasis:names:tc:SPML:2:0:password", name = "expirePasswordResponse")
    public JAXBElement<ResponseType> createExpirePasswordResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_ExpirePasswordResponse_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SuggestUsernameResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", name = "suggestUsernameResponse")
    public JAXBElement<SuggestUsernameResponseType> createSuggestUsernameResponse(SuggestUsernameResponseType value) {
        return new JAXBElement<SuggestUsernameResponseType>(_SuggestUsernameResponse_QNAME, SuggestUsernameResponseType.class, null, value);
    }

}
