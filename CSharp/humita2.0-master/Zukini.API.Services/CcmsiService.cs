using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using RestSharp;
using System.Web.Script.Serialization;

namespace Zukini.API.Services
{
    public abstract class CcmsiService
    {

        public T GetDeserializeResponse<T>(IRestResponse response)
        {
            var content = response.Content;
            var jObject = JObject.Parse(content);
            var json = JsonConvert.SerializeObject(jObject);
            var serializer = new JavaScriptSerializer();
            return serializer.Deserialize<T>(json);
        }
    }
}
