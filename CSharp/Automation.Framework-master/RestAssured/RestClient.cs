using RestAssured.Exceptions;
using System.Net;
using RestAssured.Utils;

namespace RestAssured
{
    public class RestClient
    {
        public Response GetHttpResponse(Request requestWrapper)
        {
            try
            {
                var response = (HttpWebResponse)requestWrapper.WebRequest.GetResponse();
                return new Response() { WebResponse = response};
            }

            catch (WebException ex)
            {
                throw new WebServiceResponseException(requestWrapper, ex);
            }
        }
    }
}
