using RestAssured.Utils;
using System;
using System.Net;
using System.Text;

namespace RestAssured.Exceptions
{
    public class WebServiceResponseException : Exception
    {
        public WebServiceResponseException(Request request, WebException inner) :
            base(GetMessage(request, inner))
        {

        }

        public WebServiceResponseException(string customMessage, WebException inner) :
            base(GetMessage(customMessage, inner))
        {

        }

        private static string GetMessage(Request request, WebException inner)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Error While get Response for Httpmethod: ");
            sb.Append(request.Method);
            sb.Append("\nRequest Url:");
            sb.Append(request.Url);
            sb.Append("\n\nException Message: ");
            sb.Append(inner.Message);
            return sb.ToString();
        }

        private static string GetMessage(string custommessage, WebException inner)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Exception: ");
            sb.Append(custommessage);
            sb.Append("Exception Message: ");
            sb.Append(inner.Message);
            return sb.ToString();
        }
    }
}
