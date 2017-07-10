using System.Collections.Generic;

namespace Zukini.API.Services.Ccmsi.MobileApi.Response
{
    public class RootObject
    {
        public List<object> Errors { get; set; }
        public Result Result { get; set; }
    }
}
