using System;
using System.Net;
using System.Text;

namespace RestAssured.Utils
{
    public class Request
    {
        private string ExceptionMessage = "HttpWebRequest is null. Create HttpWebRequest before setting {0}";

        internal HttpWebRequest WebRequest { get; set; }
        private string _url;
        
        public Request(string url)
        {
            _url = url;
            WebRequest = (HttpWebRequest)System.Net.WebRequest.Create(_url);
        }

        public string Url
        {
            get
            {
                return _url;
            }
        }

        public Request(Uri uri)
        {
            WebRequest = (HttpWebRequest)System.Net.WebRequest.Create(uri);
        }

        public string ContentType
        {
            get
            {
                return WebRequest.ContentType;
            }

            set
            {

                WebRequest.ContentType = value;
            }
        }

        public ICredentials Credentials
        {
            get
            {
                return WebRequest.Credentials;
            }

            set
            {
                WebRequest.Credentials = value;
            }
        }

        public string UserAgent
        {
            get
            {
                return WebRequest.UserAgent;
            }

            set
            {
                WebRequest.UserAgent = value;
            }
        }

        public string Cookie
        {
            get
            {
                return WebRequest.Headers["Cookie"];
            }

            set
            {
                if (WebRequest == null)
                {
                    throw new NullReferenceException(string.Format(ExceptionMessage, "Cookie"));
                }

                WebRequest.Headers["Cookie"] = value;
            }
        }

        public string XcsrfToken
        {
            get
            {
                return WebRequest.Headers["X-CSRF-TOKEN"];
            }

            set
            {
                WebRequest.Headers["X-CSRF-TOKEN"] = value;
            }
        }

        public string PostData { get; set; }

        public string Method
        {
            get
            {
                return WebRequest.Method;
            }

            set
            {
                WebRequest.Method = value;
            }
        }

        public void SetHeaderValue(string headerAttribute, string headerAttributeValue)
        {
            try
            {
                WebRequest.Headers[headerAttribute] = headerAttributeValue;
            }

            catch(WebException ex)
            { 
                throw new WebException(ex.Message);
            }
            
        }

        public void WriteRequestStream()
        {
            try
            {
                var encodedRequestBody = Encoding.UTF8.GetBytes(PostData);
                WebRequest.GetRequestStream().Write(encodedRequestBody, 0, encodedRequestBody.Length);
            }
            
            catch(WebException ex)
            {
                throw new WebException(ex.Message);
            }
        }
    }
}
