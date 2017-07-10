using BoDi;
using FluentAssertions;
using NUnit.Framework;
using TechTalk.SpecFlow;
using Zukini.API.Services.Ccmsi.IdentityServer.Request;
using Zukini.API.Services.Ccmsi.MobileApi.Request;
using Zukini.API.Services.Ccmsi.MobileApi.Response;
using Zukini.API.Steps;

namespace Zukini.API.Examples.Features.Steps
{
    [Binding]
    [Parallelizable(ParallelScope.Fixtures)]
    public class CcmsiMobileApiExampleFeaturesSteps : ApiSteps
    {
        public CcmsiMobileApiExampleFeaturesSteps(IObjectContainer objectContainer)
            : base(objectContainer)
        {
        }
        private string _accessToken;
        RootObject _jsonObject;

        [Given(@"an access token")]
        public void GivenAnAccessToken()
        {
            var searchIs = new SearchIdentityServer();
            _accessToken = searchIs.GetIdentityServerTokenDto().access_token;
        }

        [When(@"I search for userId ""(.*)""")]
        public void WhenISearchForUserId(string userId)
        {
            var search = new SearchCcmsiApi();
            _jsonObject = search.GetClientList(userId, _accessToken);
        }

        [When(@"I search for userId '(.*)' and clientNumber '(.*)'")]
        public void WhenISearchForUserIdAndClientNumber(string userId, string clientNumber)
        {
            var search = new SearchCcmsiApi();
            _jsonObject = search.GetClaimList(userId, clientNumber, _accessToken);
        }

     
        [Then(@"the result contains (.*) and (.*)")]
        public void ThenTheResultContains(int clientValue, string clientName)
        {
            var i = clientValue - 1;
            var clientValueResponse = int.Parse(_jsonObject.Result.EntityItemList[i].ClientValue);
            var clientNameResponse = _jsonObject.Result.EntityItemList[i].ClientName;
            clientValue.Should().Be(clientValueResponse);
            clientNameResponse.ShouldBeEquivalentTo(clientName);
        }

        [Then(@"the result must contains (.*) and (.*)")]
        public void ThenTheResultMustContains(string claimNumber, string adjusterEmail)
        {
            var claimNumberResponse = _jsonObject.Result.EntityItemList[0].ClaimNumber;
            var adjusterEmailResponse = _jsonObject.Result.EntityItemList[0].AdjusterEmail;

            claimNumber.ShouldBeEquivalentTo(claimNumberResponse);
            adjusterEmail.ShouldBeEquivalentTo(adjusterEmailResponse);                
        }
    }
}
