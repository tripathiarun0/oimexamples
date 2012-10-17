
package com.ai.oimspmlclient;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-hudson-752-
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SPMLRequestPortType", targetNamespace = "http://xmlns.oracle.com/idm/identity/webservice/SPMLService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SPMLRequestPortType {


    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ListTargetsResponseType
     */
    @WebMethod(operationName = "SPMLListTargetsRequest", action = "listTargets")
    @WebResult(name = "listTargetsResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
    public ListTargetsResponseType spmlListTargetsRequest(
        @WebParam(name = "listTargetsRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
        ListTargetsRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.AddResponseType
     */
    @WebMethod(operationName = "SPMLAddRequest", action = "add")
    @WebResult(name = "addResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
    public AddResponseType spmlAddRequest(
        @WebParam(name = "addRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
        AddRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.LookupResponseType
     */
    @WebMethod(operationName = "SPMLLookupRequest", action = "lookup")
    @WebResult(name = "lookupResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
    public LookupResponseType spmlLookupRequest(
        @WebParam(name = "lookupRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
        LookupRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ModifyResponseType
     */
    @WebMethod(operationName = "SPMLModifyRequest", action = "modify")
    @WebResult(name = "modifyResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
    public ModifyResponseType spmlModifyRequest(
        @WebParam(name = "modifyRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
        ModifyRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ResponseType
     */
    @WebMethod(operationName = "SPMLDeleteRequest", action = "delete")
    @WebResult(name = "deleteResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
    public ResponseType spmlDeleteRequest(
        @WebParam(name = "deleteRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0", partName = "body")
        DeleteRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.StatusResponseType
     */
    @WebMethod(operationName = "SPMLStatusRequest", action = "status")
    @WebResult(name = "statusResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:async", partName = "body")
    public StatusResponseType spmlStatusRequest(
        @WebParam(name = "statusRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:async", partName = "body")
        StatusRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.CancelResponseType
     */
    @WebMethod(operationName = "SPMLCancelRequest", action = "cancel")
    @WebResult(name = "cancelResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:async", partName = "body")
    public CancelResponseType spmlCancelRequest(
        @WebParam(name = "cancelRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:async", partName = "body")
        CancelRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ResponseType
     */
    @WebMethod(operationName = "SPMLSetPasswordRequest", action = "setPassword")
    @WebResult(name = "setPasswordResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:password", partName = "body")
    public ResponseType spmlSetPasswordRequest(
        @WebParam(name = "setPasswordRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:password", partName = "body")
        SetPasswordRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ResponseType
     */
    @WebMethod(operationName = "SPMLExpirePasswordRequest", action = "expirePassword")
    @WebResult(name = "expirePasswordResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:password", partName = "body")
    public ResponseType spmlExpirePasswordRequest(
        @WebParam(name = "expirePasswordRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:password", partName = "body")
        ExpirePasswordRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ResetPasswordResponseType
     */
    @WebMethod(operationName = "SPMLResetPasswordRequest", action = "resetPassword")
    @WebResult(name = "resetPasswordResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:password", partName = "body")
    public ResetPasswordResponseType spmlResetPasswordRequest(
        @WebParam(name = "resetPasswordRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:password", partName = "body")
        ResetPasswordRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ValidatePasswordResponseType
     */
    @WebMethod(operationName = "SPMLValidatePasswordRequest", action = "validatePassword")
    @WebResult(name = "validatePasswordResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:password", partName = "body")
    public ValidatePasswordResponseType spmlValidatePasswordRequest(
        @WebParam(name = "validatePasswordRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:password", partName = "body")
        ValidatePasswordRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ResponseType
     */
    @WebMethod(operationName = "SPMLResumeRequest", action = "resume")
    @WebResult(name = "resumeResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:suspend", partName = "body")
    public ResponseType spmlResumeRequest(
        @WebParam(name = "resumeRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:suspend", partName = "body")
        ResumeRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ResponseType
     */
    @WebMethod(operationName = "SPMLSuspendRequest", action = "suspend")
    @WebResult(name = "suspendResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:suspend", partName = "body")
    public ResponseType spmlSuspendRequest(
        @WebParam(name = "suspendRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:suspend", partName = "body")
        SuspendRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ActiveResponseType
     */
    @WebMethod(operationName = "SPMLActiveRequest", action = "active")
    @WebResult(name = "activeResponse", targetNamespace = "urn:oasis:names:tc:SPML:2:0:suspend", partName = "body")
    public ActiveResponseType spmlActiveRequest(
        @WebParam(name = "activeRequest", targetNamespace = "urn:oasis:names:tc:SPML:2:0:suspend", partName = "body")
        ActiveRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.LookupUsernamePolicyResponseType
     */
    @WebMethod(operationName = "SPMLLookupUsernamePolicyRequest", action = "lookupUsernamePolicy")
    @WebResult(name = "lookupUsernamePolicyResponse", targetNamespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", partName = "body")
    public LookupUsernamePolicyResponseType spmlLookupUsernamePolicyRequest(
        @WebParam(name = "lookupUsernamePolicyRequest", targetNamespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", partName = "body")
        LookupUsernamePolicyRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.SuggestUsernameResponseType
     */
    @WebMethod(operationName = "SPMLSuggestUsernameRequest", action = "suggestUsername")
    @WebResult(name = "suggestUsernameResponse", targetNamespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", partName = "body")
    public SuggestUsernameResponseType spmlSuggestUsernameRequest(
        @WebParam(name = "suggestUsernameRequest", targetNamespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", partName = "body")
        SuggestUsernameRequestType body);

    /**
     * 
     * @param body
     * @return
     *     returns com.ai.oimspmlclient.ValidateUsernameResponseType
     */
    @WebMethod(operationName = "SPMLValidateUsernameRequest", action = "validateUsername")
    @WebResult(name = "validateUsernameResponse", targetNamespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", partName = "body")
    public ValidateUsernameResponseType spmlValidateUsernameRequest(
        @WebParam(name = "validateUsernameRequest", targetNamespace = "http://xmlns.oracle.com/idm/identity/spmlv2custom/Username", partName = "body")
        ValidateUsernameRequestType body);

}