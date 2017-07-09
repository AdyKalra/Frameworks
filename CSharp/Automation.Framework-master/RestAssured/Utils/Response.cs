using RestAssured.Exceptions;
using System;
using System.IO;
using System.Net;
using Newtonsoft.Json.Linq;

namespace RestAssured.Utils
{
    public class Response
    {
        internal HttpWebResponse WebResponse { get; set; }

        public string ResponseBody
        {
            get
            {
                try
                {
                    using (var data = WebResponse.GetResponseStream())
                    {
                        if (data != null)
                        {
                            using (var reader = new StreamReader(data))
                            {
                                return reader.ReadToEnd();
                            }
                        }

                        return string.Empty;
                    }
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving Response Body.", ex);
                }
            }
        }

        public string RetrieveValueFromResponse(string key)
        {
            try
            {
                var parsed = JObject.Parse(ResponseBody);
                return parsed[key].Value<string>();
            }

            catch(WebException ex)
            {
                throw new WebServiceResponseException
                     (string.Format("Exception retrieving {0} from response Body.", key), ex);
            }
        }

        public CookieCollection Cookies
        {
            get
            {
                return WebResponse.Cookies;
            }
        } 

        public string GetResponseHeader(string headerName)
        {
            try
            {
                return WebResponse.GetResponseHeader(headerName);
            }

            catch(WebException ex)
            {
                throw new WebServiceResponseException
                    (string.Format("Exception retrieving Header {0}:", headerName), ex);
            }
            
        }

        public Uri ResponseUri
        {
            get
            {
                try
                {
                    return WebResponse.ResponseUri;
                }

                catch(WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving Response Uri", ex);
                }
            }
        }

        public HttpStatusCode StatusCode
        {
            get
            {
                try
                {
                    return WebResponse.StatusCode;
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving Response Uri", ex);
                }
            }
        }

        public string StatusDescription
        {
            get
            {
                try
                {
                    return WebResponse.StatusDescription;
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving Status Description", ex);
                }
            }
        }

        public string ContentEncoding
        {
            get
            {
                try
                {
                    return WebResponse.ContentEncoding;
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving Content Encoding.", ex);
                }
            }
        }

        public string ContentType
        {
            get
            {
                try
                {
                    return WebResponse.ContentType;
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving ContentType.", ex);
                }
            }
        }

        public long ContentLength
        {
            get
            {
                try
                {
                    return WebResponse.ContentLength;
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving ContentLength.", ex);
                }
            }
        }

        public WebHeaderCollection Headers
        {
            get
            {
                try
                {
                    return WebResponse.Headers;
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving Headers.", ex);
                }
            }
        }

        public string ServerName
        {
            get
            {
                try
                {
                    return WebResponse.Server;
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving ServerName.", ex);
                }
            }
        }

        public string HttpMethod
        {
            get
            {
                try
                {
                    return WebResponse.Method;
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving Method used to get the response.", ex);
                }
            }
        }

        public string ProtocolVersion
        {
            get
            {
                try
                {
                    return WebResponse.ProtocolVersion.ToString();
                }

                catch (WebException ex)
                {
                    throw new WebServiceResponseException
                     ("Exception retrieving Protocol Version.", ex);
                }
            }
        }
    }
}
