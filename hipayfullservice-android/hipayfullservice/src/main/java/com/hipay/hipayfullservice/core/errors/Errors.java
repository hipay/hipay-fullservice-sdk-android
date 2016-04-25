package com.hipay.hipayfullservice.core.errors;

/**
 * Created by nfillion on 24/02/16.
 */
public class Errors {

    public static final String TAG = "Errors";

    public static final String HTTPOtherDescription = "An unknown error occurred while attempting to make the HTTP request.";
    public static final String HTTPNetworkUnavailableDescription = "The network is unavailable.";
    public static final String HTTPConfigDescription = "There's a remote/local configuration error.";
    public static final String HTTPConnectionFailedDescription = "The request has been interrupted. The server may have received the request.";
    public static final String HTTPClientDescription = "Wrong parameters have been sent to the server.";
    public static final String HTTPServerDescription = "There's a server side error.";

    //public static final String HTTPPlainResponseKey = "HPFErrorCodeHTTPPlainResponseKey";
    //public static final String HTTPParsedResponseKey = "HPFErrorCodeHTTPParsedResponseKey";
    //public static final String HTTPStatusCodeKey = "HPFErrorCodeHTTPStatusCodeKey";

    //public static final String HPIDescriptionKey = "HPFErrorCodeAPIDescriptionKey";
    //public static final String HPIMessageKey = "HPFErrorCodeAPIMessageKey";
    //public static final String HPICodeKey = "HPFErrorCodeAPICodeKey";


    public enum Code {

        /// Unknown network/HTTP error
        HTTPOther(0),

        /// Network is unavailable
        HTTPNetworkUnavailable(1),

        /// Config error (such as SSL, bad URL, etc.)
        HTTPConfig(2),

        /// The connection has been interupted
        HTTPConnectionFailed(3),

        /// HTTP client error (400)
        HTTPClient(4),

        /// HTTP client error (typically a 500 error)
        HTTPServer(5),

        /// Configuration errors (refer to the HiPay Fullservice API documentation appendix)
        APIConfiguration(6),

        /// Validation errors (refer to the HiPay Fullservice API documentation appendix)
        APIValidation(7),

        /// Error codes relating to the Checkout Process (refer to the HiPay Fullservice API documentation appendix)
        APICheckout(8),

        /// Error codes relating to Maintenance Operations (refer to the HiPay Fullservice API documentation appendix)
        APIMaintenance(9),

        /// Acquirer Reason Codes (refer to the HiPay Fullservice API documentation appendix)
        APIAcquirer(10),

        /// Unknown error
        APIOther(11);

        protected final Integer code;

        Code(Integer code) {
            this.code = code;
        }

        public Integer getIntegerValue() {
            return this.code;
        }
    }


    public enum APIReason {

        // Configuration errors
        APIIncorrectCredentials (1000001),
        APIIncorrectSignature (1000002),
        APIAccountNotActive (1000003),
        APIAccountLocked (1000004),
        APIInsufficientPermissions (1000005),
        APIForbiddenAccess (1000006),
        APIUnsupportedVersion (1000007),
        APITemporarilyUnavailable (1000008),
        APINotAllowed (1000009),
        APIMethodNotAllowedGateway (1010001),
        APIInvalidParameter (1010002),
        APIMethodNotAllowedSecureVault (1010003),
        APIInvalidCardToken (1012003),
        APIRequiredParameterMissing (1010101),
        APIInvalidParameterFormat (1010201),
        APIInvalidParameterLength (1010202),
        APIInvalidParameterNonAlpha (1010203),
        APIInvalidParameterNonNum (1010204),
        APIInvalidParameterNonDecimal (1010205),
        APIInvalidDate (1010206),
        APIInvalidTime (1010207),
        APIInvalidIPAddress (1010208),
        APIInvalidEmailAddress (1010209),
        APIInvalidSoftDescriptorCodeMessage (1010301),
        APINoRouteToAcquirer (1020001),
        APIUnsupportedECIDescription (1020002),
        APIUnsupported (1020003),

        // Validation errors
        APIUnknownOrder (3000001),
        APIUnknownTransaction (3000002),
        APIUnknownMerchant (3000003),
        APIUnsupportedOperation (3000101),
        APIUnknownIPAddress (3000102),
        APISuspicionOfFraud (3000201),
        APIFraudSuspicion (3040001),
        APIUnknownToken (3030001),
        APILuhnCheckFailed (409),

        // Error codes relating to the Checkout Process
        APIUnsupportedCurrency (3010001),
        APIAmountLimitExceeded (3010002),
        APIMaxAttemptsExceeded (3010003),
        APIDuplicateOrder (3010004),
        APICheckoutSessionExpired (3010005),
        APIOrderCompleted (3010006),
        APIOrderExpired (3010007),
        APIOrderVoided (3010008),

        // Error codes relating to Maintenance Operations
        APIAuthorizationExpired (3020001),
        APIAllowableAmountLimitExceeded (3020002),
        APINotEnabled (3020101),
        APINotAllowedCapture (3020102),
        APINotAllowedPartialCapture (3020103),
        APIPermissionDenied (3020104),
        APICurrencyMismatch (3020105),
        APIAuthorizationCompleted (3020106),
        APINoMore (3020107),
        APIInvalidAmount (3020108),
        APIAmountLimitExceededCapture (3020109),
        APIAmountLimitExceededPartialCapture (3020110),
        APIOperationNotPermittedClosed (3020111),
        APIOperationNotPermittedFraud (3020112),
        APIRefundNotEnabled (3020201),
        APIRefundNotAllowed (3020202),
        APIPartialRefundNotAllowed (3020203),
        APIRefundPermissionDenied (3020204),
        APIRefundCurrencyMismatch (3020205),
        APIAlreadyRefunded (3020206),
        APIRefundNoMore (3020207),
        APIRefundInvalidAmount (3020208),
        APIRefundAmountLimitExceeded (3020209),
        APIRefundAmountLimitExceededPartial (3020210),
        APIOperationNotPermitted (3020211),
        APITooLate (3020212),
        APIReauthorizationNotEnabled (3020301),
        APIReauthorizationNotAllowed (3020302),
        APICannotReauthorize (3020303),
        APIMaxLimitExceeded (3020304),
        APIVoidNotAllowed (3020401),
        APICannotVoid (3020402),
        APIAuthorizationVoided (3020403),

        // Acquirer Reason Codes
        APIDeclinedAcquirer (4000001),
        APIDeclinedFinancialInstituion (4000002),
        APIInsufficientFundsAccount (4000003),
        APITechnicalProblem (4000004),
        APICommunicationFailure (4000005),
        APIAcquirerUnavailable (4000006),
        APIDuplicateTransaction (4000007),
        APIPaymentCancelledByTheCustomer (4000008),
        APIInvalidTransaction (4000009),
        APIPleaseCallTheAcquirerSupportCallNumber (4000010),
        APIAuthenticationFailedPleaseRetryOrCancel (4000011),
        APINoUIDConfiguredForThisOperation (4000012),
        APIRefusalNoExplicitReason (4010101),
        APIIssuerNotAvailable (4010102),
        APIInsufficientFundsCredit (4010103),
        APITransactionNotPermitted (4010201),
        APIInvalidCardNumber (4010202),
        APIUnsupportedCard (4010203),
        APICardExpired (4010204),
        APIExpiryDateIncorrect (4010205),
        APICVCRequired (4010206),
        APICVCError (4010207),
        APIAVSFailed (4010301),
        APIRetainCard (4010302),
        APILostOrStolenCard (4010303),
        APIRestrictedCard (4010304),
        APICardLimitExceeded (4010305),
        APICardBlacklisted (4010306),
        APIUnauthorisedIPAddressCountry (4010307),
        APICardnotInAuthorisersDatabase (4010309);


        protected final Integer reason;

        APIReason(Integer reason) {
            this.reason = (reason);
        }

        public Integer getIntegerValue() {
            return this.reason;
        }
    }
}
