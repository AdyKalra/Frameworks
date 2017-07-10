using BoDi;
using Coypu;
using Zukini.Steps;

namespace Zukini.UI.Steps
{
    public abstract class UiSteps : BaseSteps
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="UiSteps"/> class.
        /// </summary>
        /// <param name="objectContainer">The object container.</param>
        protected UiSteps(IObjectContainer objectContainer) :
            base(objectContainer)
        {
        }

        /// <summary>
        /// Returns the IWebDriver instance as registered with the ObjectContainer.
        /// </summary>
        protected BrowserSession Browser => ObjectContainer.Resolve<BrowserSession>();
    }
}
