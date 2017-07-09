using System;

namespace Selenium.Core.Framework.Utilities
{
    public class FindsByAttribute : Attribute
    {
        public How How;
        public string Using;

        public string Page;
        public string Field;

        public string[] parameters;
    }

    public enum How
    {
        Id,
        Name,
        Xpath,
        TagName,
        CssSelector,
        LinkText,
        ClassName,
        PartialLinkText
    }

    internal class DriverPlatform
    {
        public const string Chrome = "Chrome";
        public const string Firefox = "Firefox";
        public const string InternetExplorer = "InternetExplorer";
        public const string Winium = "Winium";
    }
}
