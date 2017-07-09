using System;
using System.Text;

namespace Selenium.Core.Framework.Exceptions
{
    public class BrowserInitializationException : Exception
    {
        public BrowserInitializationException(string browser, Exception inner) :
            base(GetMessage(browser, inner))
        {

        }

        private static string GetMessage(string browser, Exception inner)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Error initializing browser: ");
            sb.Append(browser);
            sb.Append(".Exception Message: ");
            sb.Append(inner.Message);
            return sb.ToString();
        }
    }
}
