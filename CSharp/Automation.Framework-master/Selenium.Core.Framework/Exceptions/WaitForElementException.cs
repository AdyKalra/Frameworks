using Selenium.Core.Framework.Utilities;
using System;
using System.Text;

namespace Selenium.Core.Framework.Exceptions
{
    public class WaitForElementException : Exception
    {
        public WaitForElementException
           (How findBy, string findByValue, Exception inner) :
            base(GetMessage(findBy, findByValue, inner))
        {

        }

        public WaitForElementException
           (string constraint, string constraintValue, Exception inner) :
            base(GetMessage(constraint, constraintValue, inner))
        {

        }

        private static string GetMessage(How findBy, string findByValue, Exception inner)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Exception while Waiting for the Element with FindBy");
            sb.Append(findBy.ToString());
            sb.Append(" and findbyvalue: ");
            sb.Append(findByValue);
            sb.Append(".Exception Message:");
            sb.Append(inner.Message);
            return sb.ToString();
        }

        private static string GetMessage(string constraint, string constraintValue, Exception inner)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Exception while waiting for: ");
            sb.Append(constraint);
            sb.Append(" to be:");
            sb.Append(constraintValue);
            sb.Append(".Exception Message:");
            sb.Append(inner.Message);
            return sb.ToString();
        }

    }
}
