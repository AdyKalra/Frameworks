using BoDi;
using System;
using TechTalk.SpecFlow;

namespace Zukini
{
    [Binding]
    public class Hooks
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="Hooks"/> class.
        /// </summary>
        /// <param name="objectContainer">The object container (Injected with DI).</param>
        /// <param name="scenarioContext"></param>
        /// <param name="featureContext"></param>
        public Hooks(IObjectContainer objectContainer, ScenarioContext scenarioContext, FeatureContext featureContext)
        {
            ObjectContainer = objectContainer;
            ScenarioContext = scenarioContext;
            FeatureContext = featureContext;
        }

        /// <summary>
        /// Gets the object container used for dependency injection.
        /// </summary>
        protected IObjectContainer ObjectContainer { get; }

        /// <summary>
        /// Provides the ScenarioContext for the currently executing scenario.
        /// </summary>
        protected ScenarioContext ScenarioContext { get; }

        /// <summary>
        /// Gets the feature context for the currently executing feature.
        /// </summary>
        protected FeatureContext FeatureContext { get; }


        /// <summary>
        /// Global BeforeScenario hook used to new up the WebDriver instance
        /// prior to each test.
        /// </summary>
        [BeforeScenario]
        protected void BeforeScenario()
        {
            // Create a property bucket so we have a place to store values between steps
            var propertyBucket = ObjectContainer.Resolve<PropertyBucket>();

            Console.WriteLine("Unique Test Id: {0}", propertyBucket.TestId);
        }
    }
}
