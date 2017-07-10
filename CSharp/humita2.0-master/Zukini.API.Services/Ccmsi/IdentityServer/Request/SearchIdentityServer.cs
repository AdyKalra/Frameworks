using System;
using RestSharp;
using Zukini.API.Services.Ccmsi.IdentityServer.Response;

namespace Zukini.API.Services.Ccmsi.IdentityServer.Request
{
    public class SearchIdentityServer : CcmsiService
    {
        public Uri BaseApiUrl => new Uri(TestSettings.ApiMobileUrlDev);

        public IdentityServerTokenDto GetIdentityServerTokenDto()
        {
            var client = new RestClient(TestSettings.IdentityServerApiUrlDev);
            var request = new RestRequest("/connect/token", Method.POST);
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("content-type", "application/x-www-form-urlencoded");
            request.AddParameter("application/x-www-form-urlencoded", "grant_type=password&username=sblakeney&password=Devpass2&scope=ccmsimobileapi%20offline_access&client_id=ccmsiapi&client_secret=z3oKWgpX4JZulVlgnCCX7ffDkNbQH4zV", ParameterType.RequestBody);
            var response = client.Execute(request);
           
            return GetDeserializeResponse<IdentityServerTokenDto>(response);

        }
    }
}
