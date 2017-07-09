using Selenium.Core.Framework.Utilities;
using System;
using System.Text;

namespace Selenium.Core.Framework.Exceptions
{
    public class NoSuchElementFoundException : Exception
    {
        public NoSuchElementFoundException
            (How findBy, string findByValue, Exception inner) :
            base(GetMessage(findBy, findByValue, inner))
        {
            
        }

        private static string GetMessage(How findBy, string findByValue, Exception inner)
        {
            StringBuilder sb = new StringBuilder();
            sb.Append("Couldn't locate element with findBy:");
            sb.Append(findBy.ToString());
            sb.Append(" and findbyvalue: ");
            sb.Append(findByValue);
            sb.Append(".Exception Message:");
            sb.Append(inner.Message);
            return sb.ToString();
        }
    }
}
