using System.Configuration;

namespace Zukini.UI.Examples.Pages
{
    /// <summary>
    /// Helper class to make getting at the test settings easy. 
    /// To reference, simply use TestSettings.Property (e.g. TestSettings.ApplicationUrl).
    /// </summary>
    public static class PageSettings
    {
        public static string ClaimViewUrl => ConfigurationManager.AppSettings["ClaimViewUrl"];
        public static string GoogleUrl => ConfigurationManager.AppSettings["GoogleUrl"];
        public static string GoogleHttpUrl => ConfigurationManager.AppSettings["GoogleHttpUrl"];
        public static string W3SchoolsBaseUrl => ConfigurationManager.AppSettings["W3SchoolsBaseUrl"];
        public static string JsonPlaceholderApiUrl => ConfigurationManager.AppSettings["JsonPlaceholderApiUrl"];
        public static string GridUrl => ConfigurationManager.AppSettings["GridUrl"];
    }
}
