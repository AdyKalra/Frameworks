namespace Zukini.API.Services.Ccmsi.MobileApi.Response
{
    public class EntityItemList
    {
        public string ClientValue { get; set; }
        public string ClientName { get; set; }
        public string ClaimNumber { get; set; }
        public string MsiClaimNumber { get; set; }
        public string ClaimantLastName { get; set; }
        public string ClaimantFirstName { get; set; }
        public string ClaimantAddress { get; set; }
        public string ClaimStatus { get; set; }
        public string ClaimType { get; set; }
        public string CoverageCode { get; set; }
        public double TotalPaid { get; set; }
        public double OutstandingReserve { get; set; }
        public double TotalIncurred { get; set; }
        public string PrimaryInsuranceCompany { get; set; }
        public string IssuingInsuranceCompany { get; set; }
        public string PolicyNumber { get; set; }
        public string PolicyEffectiveDate { get; set; }
        public double SirOrDeductibleAmount { get; set; }
        public string Adjuster { get; set; }
        public string AdjusterEmail { get; set; }
        public string LossType { get; set; }
        public string BodyPart { get; set; }
        public string CauseCode { get; set; }
        public double CurrentAge { get; set; }
        public double CraScore { get; set; }
        public string DateOfHire { get; set; }
        public double AwwRate { get; set; }
        public double TtdRate { get; set; }
        public double PpdRate { get; set; }
        public string StateOfJurisdiction { get; set; }
        public string MedicareEligibilityStatus { get; set; }
        public double AgeAtTimeOfAccident { get; set; }
        public string AccidentDescription { get; set; }
        public string DateOfLoss { get; set; }
        public string ClaimantReportDate { get; set; }
        public string ClaimSubmittedDate { get; set; }
        public string ClaimEntryDate { get; set; }
        public object DateOpened { get; set; }
        public object DateClosed { get; set; }
        public string ClaimDenied { get; set; }
        public string UnderInvestigation { get; set; }
        public string ClaimControverted { get; set; }
        public string AttorneyRepresented { get; set; }
        public string PlaintiffAttorney { get; set; }
        public string DefenseAttorney { get; set; }
        public string MemberNumber { get; set; }
    }
}