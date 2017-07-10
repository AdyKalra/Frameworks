using System.Collections.Generic;

namespace Zukini.API.Services.Ccmsi.MobileApi.Response
{
    public class Result
    {
        public List<EntityItemList> EntityItemList { get; set; }
        public int TotalPages { get; set; }
        public int TotalRows { get; set; }
        public int CurrentPage { get; set; }
        public int NextPage { get; set; }
        public object PreviousPage { get; set; }
    }
}