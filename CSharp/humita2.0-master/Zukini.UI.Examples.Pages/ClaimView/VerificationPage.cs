using Coypu;
using FluentAssertions;
using Zukini.UI.Pages;

namespace Zukini.UI.Examples.Pages.ClaimView
{
    public class VerificationPage : BasePage
    {
        private readonly BrowserSession _browser;

        public VerificationPage(BrowserSession browser) : base(browser)
        {
            _browser = browser;
        }

        public ElementScope GenerateCodeButton => _browser.FindXPath(GenerateCodeButtonLocator);
        public readonly string GenerateCodeButtonLocator = ".//div/button[@class='btn access-btn-warning ng-scope']";

        public void ValidateGenerateCodeButtonText()
        {
            GenerateCodeButton.Text.Should().Be("Generate Code");
        }

    }
}
