using System.Net;

namespace RestAssured.Utils
{
    public class Credentials
    {
        public static ICredentials DefaultCredentials()
        {
            return CredentialCache.DefaultCredentials;
        }

        public static ICredentials DefaultNetworkCredentials()
        {
            return CredentialCache.DefaultNetworkCredentials;
        }

        public static NetworkCredential NetworkCredentials(string username, string password)
        {
            return new NetworkCredential(username, password);
        }

        public static NetworkCredential NetworkCredentials(string username, string password, string domain)
        {
            return new NetworkCredential(username, password, domain);
        }
    }
}
