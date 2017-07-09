using System;
using System.Text;

namespace Selenium.Core.Framework.Exceptions
{
    public class DriverDisposeException : Exception
    {
        public DriverDisposeException(Exception inner) : base(GetMessage(inner))
        {

        }

        private static string GetMessage(Exception inner)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Couldn't Dispose WebDriver. Exception Mesage:");
            sb.Append(inner.Message);
            return sb.ToString();
        }
    }
}
