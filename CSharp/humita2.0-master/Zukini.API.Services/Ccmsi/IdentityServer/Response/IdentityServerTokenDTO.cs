namespace Zukini.API.Services.Ccmsi.IdentityServer.Response
{
    public class IdentityServerTokenDto
    {
        public string access_token { get; set; }
        public int expires_in { get; set; }
        public string token_type { get; set; }
        public string refresh_token { get; set; }
    }
}