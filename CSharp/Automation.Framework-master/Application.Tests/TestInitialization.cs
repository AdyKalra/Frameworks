using TechTalk.SpecFlow;

namespace Application.Tests
{
    [Binding]
    public class TestInitialization
    {
        [BeforeTestRun]
        public static void BeforeTestRum()
        {
            TestBase testBase = new TestBase();
        }

        [AfterTestRun]
        public static void AfterTestRun()
        {
            TestBase.Driver.DisposeDriver();
        }
    }
}
