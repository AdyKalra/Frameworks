using System.Configuration;

namespace Zukini.API.Services
{
    /// <summary>
    /// Helper class to make getting at the test settings easy. 
    /// To reference, simply use TestSettings.Property (e.g. TestSettings.ApplicationUrl).
    /// </summary>
    public static class TestSettings
    {
        public static string ApiMobileUrlDev => ConfigurationManager.AppSettings["CCMSIMobileApiUrlDev"];
        public static string ApiMobileUrlUat => ConfigurationManager.AppSettings["CCMSIMobileApiUrlTest"];

        public static string IdentityServerApiUrlUat => ConfigurationManager.AppSettings["IdentityServerApiUrlTest"];
        public static string IdentityServerApiUrlDev => ConfigurationManager.AppSettings["IdentityServerApiUrlDev"];

        public static string JsonPlaceholderApiUrl => ConfigurationManager.AppSettings["JsonPlaceholderApiUrl"];
    }
}
