package com.hipay.fullservice.core.errors;

/**
 * Created by nfillion on 24/02/16.
 */
public class Errors {

    public static final String TAG = "Errors";

    public static final String HTTPOtherDescription = "An unknown error occurred while attempting to make the HTTP request.";
    public static final String HTTPNetworkUnavailableDescription = "The network is unavailable.";
    public static final String HTTPConfigDescription = "There's a remote/local configuration error.";
    //public static final String HTTPConnectionFailedDescription = "The request has been interrupted. The server may have received the request.";
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
        //HTTPConnectionFailed(3),

        /// HTTP client error (400)
        HTTPClient(3),

        /// HTTP client error (typically a 500 error)
        HTTPServer(4),

        /// Configuration errors (refer to the HiPay Fullservice API documentation appendix)
        APIConfiguration(5),

        /// Validation errors (refer to the HiPay Fullservice API documentation appendix)
        APIValidation(6),

        /// Error codes relating to the Checkout Process (refer to the HiPay Fullservice API documentation appendix)
        APICheckout(7),

        /// Error codes relating to Maintenance Operations (refer to the HiPay Fullservice API documentation appendix)
        APIMaintenance(8),

        /// Acquirer Reason Codes (refer to the HiPay Fullservice API documentation appendix)
        APIAcquirer(9),

        /// Unknown error
        APIOther(10);

        protected final Integer code;

        Code(Integer code) {
            this.code = code;
        }

        public Integer getIntegerValue() {
            return this.code;
        }
    }


}
