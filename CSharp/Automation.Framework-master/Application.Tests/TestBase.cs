using Microsoft.Practices.Unity;
using Selenium.Core.Framework.Driver;

namespace Application.Tests
{
    public class TestBase
    {
        private static readonly IUnityContainer _unityContainer;
        private static UiDriver _driver;

        public static IUnityContainer Unity
        {
            get { return _unityContainer; }
        }

        public static UiDriver Driver
        {
            get { return _driver; }
        }

        public static object NativeWebDriver { get; private set; }

        static TestBase()
        {
            _unityContainer = new UnityContainer();
            _driver = _unityContainer.Resolve<UiDriver>();
        }
    }
}
