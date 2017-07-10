
namespace Zukini.UI
{
    public class ZukiniUiConfiguration
    {
        /// <summary>
        /// Get or set the path to where screenshots should be saved.
        /// </summary>
        public string ScreenshotDirectory { get; set; } = "Screenshots";

        /// <summary>
        /// Get or set whether to maximize the browser when starting up.
        /// </summary>
        public bool MaximizeBrowser { get; set; } = true;
    }
}
