using System;
using RestSharp;
using Zukini.API.Services.Ccmsi.MobileApi.Response;

namespace Zukini.API.Services.Ccmsi.MobileApi.Request
{
    public class SearchCcmsiApi : CcmsiService
    {
        public Uri BaseApiUrl => new Uri(TestSettings.ApiMobileUrlDev);

        public RootObject GetClientList(string userId, string accessToken)
        {
            var client = new RestClient(BaseApiUrl + "/Clients?userId=" + userId);
            var response = GetResponseWithaddAuthorizationHeaders(client, accessToken);
            return GetDeserializeResponse<RootObject>(response);
        }

        public RootObject GetClaimList(string userId, string clientNumber, string accessToken)
        {
            var client = new RestClient(BaseApiUrl + "/Claims?clientNumber=" + clientNumber + "&userId=" + userId);
            var response = GetResponseWithaddAuthorizationHeaders(client, accessToken);
            return GetDeserializeResponse<RootObject>(response);
        }

        private static IRestResponse GetResponseWithaddAuthorizationHeaders(IRestClient client, string accessToken)
        {
            var request = new RestRequest(Method.GET);
            request.AddHeader("cache-control", "no-cache");
            request.AddHeader("authorization", "Bearer " + accessToken);
            var response = client.Execute(request);
            return response;
        }

    }
}
