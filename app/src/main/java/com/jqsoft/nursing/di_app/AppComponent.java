package com.jqsoft.nursing.di_app;

import com.jqsoft.nursing.arcface.HeadCollectActivityComponent;
import com.jqsoft.nursing.arcface.HeadCollectActivityModule;
import com.jqsoft.nursing.di.component.*;
import com.jqsoft.nursing.di.component.nursing.CaseListActivityComponent;
import com.jqsoft.nursing.di.component.nursing.DeanCockpitBedFragmentComponent;
import com.jqsoft.nursing.di.component.nursing.DeanCockpitElderFragmentComponent;
import com.jqsoft.nursing.di.component.nursing.DeanCockpitFinanceFragmentComponent;
import com.jqsoft.nursing.di.component.nursing.HealthListFragmentComponent;
import com.jqsoft.nursing.di.component.nursing.IndexElderFragmentComponent;
import com.jqsoft.nursing.di.component.nursing.NursingDetailActivityComponent;
import com.jqsoft.nursing.di.component.nursing.RoundDetailActivityComponent;
import com.jqsoft.nursing.di.component.nursing.RoundFragmentNewComponent;
import com.jqsoft.nursing.di.component.nursing.SpecificNursingFragmentComponent;
import com.jqsoft.nursing.di.module.*;
import com.jqsoft.nursing.di.module.nursing.CaseListFragmentModule;
import com.jqsoft.nursing.di.module.nursing.DeanCockpitBedFragmentModule;
import com.jqsoft.nursing.di.module.nursing.DeanCockpitElderFragmentModule;
import com.jqsoft.nursing.di.module.nursing.DeanCockpitFinanceFragmentModule;
import com.jqsoft.nursing.di.module.nursing.HealthListFragmentModule;
import com.jqsoft.nursing.di.module.nursing.IndexElderFragmentModule;
import com.jqsoft.nursing.di.module.nursing.NursingDetailActivityModule;
import com.jqsoft.nursing.di.module.nursing.RoundDetailActivityModule;
import com.jqsoft.nursing.di.module.nursing.SpecificNursingFragmentModule;
import com.jqsoft.nursing.di_http.http.OkhttpModule;
import com.jqsoft.nursing.di_http.http.RetrofitModule;
import com.jqsoft.nursing.di_http.http.nursing.GCAServiceModule;

import javax.inject.Singleton;

import dagger.Component;

//import com.jqsoft.nursing.di.component.ChatActivityComponent;
//import com.jqsoft.nursing.di.module.ChatActivityModule;

//import com.jqsoft.nursing.di.component.BpartFragmentComponent;

//import com.jqsoft.nursing.di.component.MedicalInstitutionDetailActivityComponent;

/**
 * <b>类名称：</b> AppComponent <br/>
 * <b>类描述：</b> 类似与Spring的Context上下文，提供依赖注入的对象<br/>
 * <b>创建人：</b> Lincoln <br/>
 * <b>修改人：</b> Lincoln <br/>
 * <b>修改时间：</b> 2016年08月11日 下午5:22<br/>
 * <b>修改备注：</b> <br/>
 *
 * @version 1.0.0 <br/>
 */
@Singleton
@Component(modules = {AppModule.class,
        OkhttpModule.class,
        RetrofitModule.class,
        GCAServiceModule.class
})
public interface AppComponent {

    AddFamilyMemberActivityComponent addAddingFamilyMemberActivity(AddFamilyMemberActivityModule module);

    FamilyMemberActivityComponent addFamilyMemberActivity(FamilyMemberActivityModule module);

    ModuleListFragmentComponent addModuleListFragment(ModuleListFragmentModule module);

    RetrievePasswordComponent addRetrievePassword(RetrievePasswordModule retrievePasswordModule);

    RegisterComponent addRegister(RegisterModule registerModule);

    SRCLoginComponent addLogin(SRCLoginModule loginModule);

    SaveFaceInfoComponent addSaveFaceInfo(SaveFaceInfoModule loginModule);
//    LoginComponent addLogin(LoginModule loginModule);

    SubsistenceVarianceRankingStatisticsFragmentComponent addSubsistenceVarianceRankingStatisticsFragment(SubsistenceVarianceRankingStatisticsFragmentModule module);

    SubsistenceApproveRankingStatisticsFragmentComponent addSubsistenceApproveRankingStatisticsFragment(SubsistenceApproveRankingStatisticsFragmentModule module);

    SubsistenceApproveTrendStatisticsFragmentComponent addSubsistenceApproveTrendStatisticsFragment(SubsistenceApproveTrendStatisticsFragmentModule module);

    SubsistenceVarianceTrendStatisticsFragmentComponent addSubsistenceVarianceTrendStatisticsFragment(SubsistenceVarianceTrendStatisticsFragmentModule module);

    SubsistenceApprovePovertyReasonStatisticsFragmentComponent addPovertyReasonStatisticsFragment(SubsistenceApprovePovertyReasonStatisticsFragmentModule module);

    SubsistenceArchiveRankingStatisticsFragmentComponent addSubsistenceArchiveRankingStatisticsFragment(SubsistenceArchiveRankingStatisticsFragmentModule module);

    InstitutionRankingStatisticsFragmentComponent addInstitutionRankingStatisticsFragment(InstitutionRankingStatisticsFragmentModule module);

    SubsistenceArchiveTrendStatisticsFragmentComponent addSubsistenceArchiveTrendStatisticsFragment(SubsistenceArchiveTrendStatisticsFragmentModule module);

    SubsistenceArchiveHealthClassificationStatisticsFragmentComponent addSubsistenceArchiveHealthClassificationStatisticsFragment
            (SubsistenceArchiveHealthClassificationStatisticsFragmentModule module);

    InstitutionCharacterClassificationStatisticsFragmentComponent addInstitutionCharacterClassificationStatisticsFragment
            ( InstitutionCharacterClassificationStatisticsFragmentModule module);

    InstitutionServerClassificationStatisticsFragmentComponent addInstitutionServerClassificationStatisticsFragment
            ( InstitutionServerClassificationStatisticsFragmentModule module);

    InstitutionLegalPersonClassificationStatisticsFragmentComponent addInstitutionLegalPersonClassificationStatisticsFragment
            ( InstitutionLegalPersonClassificationStatisticsFragmentModule module);

    SubsistenceArchiveAbilityClassificationStatisticsFragmentComponent addSubsistenceArchiveAbilityClassificationStatisticsFragment
            (SubsistenceArchiveAbilityClassificationStatisticsFragmentModule module);

    SubsistenceArchiveAgeClassificationStatisticsFragmentComponent addSubsistenceArchiveAgeClassificationStatisticsFragment
            (SubsistenceArchiveAgeClassificationStatisticsFragmentModule module);

    SubsistenceAccountRankingStatisticsFragmentComponent addSubsistenceAccountRankingStatisticsFragment(SubsistenceAccountRankingStatisticsFragmentModule module);

    SubsistenceStandardRankingStatisticsFragmentComponent addSubsistenceStandardRankingStatisticsFragment(SubsistenceStandardRankingStatisticsFragmentModule module);

    SubsistenceStandardAverageRankingStatisticsFragmentComponent addSubsistenceStandardAverageRankingStatisticsFragment(SubsistenceStandardAverageRankingStatisticsFragmentModule module);

    SubsistenceAccountTrendStatisticsFragmentComponent addSubsistenceAccountTrendStatisticsFragment(SubsistenceAccountTrendStatisticsFragmentModule module);

    SubsistenceAccountIncreaseRatioStatisticsFragmentComponent addSubsistenceAccountIncreaseRatioStatisticsFragment(SubsistenceAccountIncreaseRatioStatisticsFragmentModule module);


    MedicalAssistantMoneyConstitutionStatisticsFragmentComponent addMedicalAssistantMoneyConstitutionStatisticsFragment
            (MedicalAssistantMoneyConstitutionStatisticsFragmentModule module);

    MedicalAssistantDirectOutcomeStatisticsFragmentComponent addMedicalAssistantDirectOutcomeStatisticsFragment(
            MedicalAssistantDirectOutcomeStatisticsFragmentModule module);

    MedicalAssistantFinanceAssuranceStatisticsFragmentComponent addMedicalAssistantFinanceAssuranceStatisticsFragment(
            MedicalAssistantFinanceAssuranceStatisticsFragmentModule module);

    TempDisasterAssistantStatisticsFragmentComponent addTempDisasterAssistantStatisticsFragment(
            TempDisasterAssistantStatisticsFragmentModule module
    );

    TempDisasterAssistancePercentageStatisticsFragmentComponent addTempDisasterAssistancePercentageStatisticsFragment
            (TempDisasterAssistancePercentageStatisticsFragmentModule module);

    FamilyEconomyCheckRankingStatisticsFragmentComponent addFamilyEconomyCheckRankingStatisticsFragment
            (FamilyEconomyCheckRankingStatisticsFragmentModule module);

    FamilyEconomyCheckTrendStatisticsFragmentComponent addFamilyEconomyCheckTrendStatisticsFragment
            (FamilyEconomyCheckTrendStatisticsFragmentModule module);

    FamilyEconomyCheckShareIndexStatisticsFragmentComponent addFamilyEconomyCheckShareIndexStatisticsFragment
            (FamilyEconomyCheckShareIndexStatisticsFragmentModule module);

    FamilyEconomyCheckProjectCheckStatisticsFragmentComponent addProjectCheckFragment
            (FamilyEconomyCheckProjectCheckStatisticsFragmentModule module);

    PolicyActivityComponent addPolicyActivity(PolicyActivityModule policyActivityModule);

    MapServiceActivityComponent addMapServiceActivity(MapServiceActivityModule module);

    NotificationActivityComponent addNotificationActivity(NotificationActivityModule notificationActivityModule);
//    ClientChatComponent  addClientChat(CLientChatModule cLientChatModule);

    PolityActivityComponent addPolityActivity(PolityActivityModule module);
    ReceptionDetailNewListActivityComponent addReceptionDetailNewListActivity(ReceptionDetailNewListActivityModule module);

    ReceptionActivityComponent addReceptionActivity(ReceptionActivityModule module);

    PersonCollectionActivityComponent addPersonCollectionActivity(PersonCollectionActivityModule module);
    SchedulingFragmentComponent addSchedulingFragment(SchedulingFragmentModule module);
    SchedulingActivityComponent addSchedulingActivity(SchedulingActivityModule module);
    UseCollectionFragmentComponent addUseCollectionFragment(UseCollectionFragmentModule module);

    ReceptionListActivityComponent addReceptionListActivity(ReceptionListActivityModule module);

    MyMessageActivityComponent addMyMessageActivity(MyMessageActivityModule module);

//    QuestionActivityComponent addQuestionActivity(QuestionActivityModule module);

    GuideActivityComponent addGuideActivity(GuideActivityModule module);

    ChangePasswordActivityComponent addChangePasswordActivity(ChangePasswordActivityModule module);

    ReliefItemActivityComponent addReliefItemActivity(ReliefItemActivityModule module);


    MyMessageDetailActivityComponent addMyMessageDetailActivity(MyMessageDetailActivityModule module);

    ReceptionDetailActivityComponent addReceptionDetailActivity(ReceptionDetailActivityModule module);

    IgGuideActivityComponent addIgGuideActivity(IgGuideActivityModule module);

    CoreIndexComponent addcoreinddex(CoreIndexActivityModule coreIndexActivityModule);


    ReserverComponent addreserver(ReserverActivityModule coreIndexActivityModule);

    ModifyCoreIndexComponent addmodifycoreinddex(CoreIndexActivityModule coreIndexActivityModule);

    ServicePackDetailComponent addservicepackdetail(ServicePackDetailActivityModule servicepackdetailActivityModule);

    /*SearchDetailComponent addsearchdetail(SearchDetailActivityModule searchdetailActivityModule);*/

//    PendExecuComponent addpendexecu(PendExecuActivityModule pendexecuActivityModule);

  //  ReservationComponent addreservation(ReservationActivityModule reservationActivityModule);

   // SignSeverPakesComponent addserverpakes(SignSeverPakesActivityModule severPakesActivityModule);

    //SignSeverPakesComponent2 addserverpakes2(SignSeverPakesActivityModule severPakesActivityModule);

   // SignClientSeverPakesComponent addclientServerpakes(SignClientSeverPakesActivityModule signClientSeverPakesActivityModule);

    IndexFragmentComponent addIndexFragment(IndexFragmentModule indexFragmentModule);

    SpecificNursingFragmentComponent addSpecificNursingFragment(SpecificNursingFragmentModule module);

    DeanCockpitElderFragmentComponent addDeanCockpitElderFragment(DeanCockpitElderFragmentModule module);

    DeanCockpitBedFragmentComponent addDeanCockpitBedFragment(DeanCockpitBedFragmentModule module);

    DeanCockpitFinanceFragmentComponent addDeanCockpitFinanceFragment(DeanCockpitFinanceFragmentModule module);

    NursingDetailActivityComponent addNursingDetailActivity(NursingDetailActivityModule module);

    OnlineChatFragmentComponent addchatFragment(OnlineChatingFragmentModule onlineChatingFragmentModule);

    OnlineChatActivityComponent addchatActivity(OnlineChatingActivityModule onlineChatingActivityModule);

    SmartAlertActivityComponent addSmartAlertActivity(SmartAlertActivityModule smartAlertActivityModule);

    //InHospitalInspectFragmentComponent addInHospitalInspectFragment(InHospitalInspectFragmentModule inHospitalInspectFragmentModule);

    //BpartFragmentComponent addBapartFragment(BFragmentModule submitSignFragmentModule);

 //   SignTeamFragmentComponent addsignteamFragment(SignTeamFragmentModule signTeamFragmentModule);

    //SaveFamilyDoctorSignFragmentComponent addCApartFragment(SaveFamilyDoctorSignFragmentModule saveFamilyDoctorSignFragmentModule);


//    ChatActivityComponent addChatActivity(ChatActivityModule chatActivityModule);

    ArcFaceListActivityComponent addArcFaceListActivity(ArcFaceListActivityModule medicalInstitutionActivityModule);

    ArcFaceListActivityNewComponent addArcFacenewListActivity(ArcFaceListActivityModule medicalInstitutionActivityModule);

    HeadCollectActivityComponent addHeadCollectActivity(HeadCollectActivityModule module);

    MedicalInstitutionActivityComponent addMedicalInstitutionActivity(MedicalInstitutionActivityModule medicalInstitutionActivityModule);

//    MedicalInstitutionDetailActivityComponent addMedicalInstitutionDetailActivity(MedicalInstitutionDetailActivityModule medicalInstitutionDetailActivityModule);


    ExecutionProjectsActivityComponent addExecutionProjectsActivity(ExecutionProjectsActivityModule executionProjectsActivityModule);
    //SignSeverPakesComponent addSignServerPakes(SignSeverPakesActivityModule signSeverPakesActivityModule );

    //OnlineConsultationActivityComponent addOnlineConsultationActivity(OnlineConsultationActivityModule module);

    SocialAssistanceObjectActivityComponent addSocialAssistanceObjectActivity(SocialAssistanceObjectActivityModule module);
    SocialHistoryActivityComponent addSocialHistorActivity(SocialHistoryActivityModule module);

    AddServeryActivityComponent addServeryactivity(AddServeryActivityModule module);

    FamilyDetailActivityComponent addfaimilydetailactivity(FamilyDetailsActivityModule module);

    DispalyBaseInfoComponent adddisplay(DispalyInfoActivityModule module);

    AddDemoCraticComponent addDemocratic(AddDemocraticModule module);

    DisPlayDemocraticComponent addDisplayDemoCratic(DisPlayDemocraticModule module);

    HandleProgressComponent addhandleprogress(HandleProgressModule module);
    HistoryDetailComponent addhisdetailPage(HistoryDetailModule module);
    SocialDetailActivityComponent addSocailDetail(SocialDetailActivityModule socialDetailActivityModule);

    //    AppointmentRegistrationActivityComponent addAppointmentRegistrationActivity(AppointmentRegistrationActivityModule module);
    HandleProgressDetailActivityComponent addprogressDetail(HandleProgressDetailActivityModule module);

    MyInfoComponent addMyInfoActivity(MyInfoModule myInfoModule);

    PersonalInfoActivityComponent addPersonalInfoActivity(PersonalInfoActivityModule myInfoModule);

    AboutInfoActivityComponent addAboutInfoActivity(AboutInfoActivityModule myInfoModule);

    SignedResidentDirectoryFragmentComponent addSignedResidentDirectoryFragment(SignedResidentDirectoryFragmentModule module);

    TownLevelMedicalInstitutionDirectoryFragmentComponent addTownLevelMedicalInstitutionDirectoryFragment(TownLevelMedicalInstitutionDirectoryFragmentModule module);

    VillageLevelMedicalInstitutionDirectoryActivityComponent addVillageLevelMedicalInstitutionDirectoryActivity(VillageLevelMedicalInstitutionDirectoryActivityModule module);

    MedicalPersonDirectoryActivityComponent addMedicalPersonDirectoryActivity(MedicalPersonDirectoryActivityModule module);

    MySignInfoActivityComponent addMySignInfoActivity(MySignInfoActivityModule module);

    //IntelligentHonourAgreementRemindActivityComponent addIntelligentHonourAgreementRemindActivity(IntelligentHonourAgreementRemindActivityModule module);

    SignServiceAssessFragmentComponent addSignServiceAssessFragment(SignServiceAssessFragmentModule module);

    SignServiceAssessActivityComponent addSignServiceAssessActivity(SignServiceAssessActivityModule module);

    HouseHoldServeyActivityComponent addHouseholdActivity(HouseHoldServeyActivityModule module);

    HouseHoldServeyBaseComponent addhouseholdbaseFragment(HouseHoldServeyBaseModule module);

    HouseHoldFamilyComponent addfamilyFragment(HouseHoldFamilyModule module);

    HouseHoldFileComponent addfileFragment(HouseHoldFileModule module);

    HouseHoldServeryComponent addserveryFragment(HouseHoldserveryModule module);

    DemocraticAppraisalComponent adddemocraticFragment(DemocraticAppraisalModule module);

    SignClientServiceAssessActivityComponent addSignClientActity(SignClientServiceAssessActivityModule module);

    SignServiceIncomeFragmentComponent addSignServiceIncomeFragment(SignServiceIncomeFragmentModule module);

    SignServiceIncomeActivityComponent addSignServiceIncomeActivity(SignServiceIncomeActivityModule module);

    //SignApplicationActivityComponent addSignApplicationActivity(SignApplicationActivityModule module);

    SignServiceEvalutionComponent addSignServiceEvalutionActivity(SignServiceEvaluteActivityModule module);

    //OnlineSignApplicationComponent addOnlineSignApplicationActity(OnlineSignApplicationActivityModule module);

    SignAgreementComponent addSignAgreenmentActivity(SignAgreementActivityModule signAgreementActivityModule);

    //PeopleBaseInfoFragmentComponent addPeopleBaseInfoFragment(PeopleBaseInfoFragmentModule module);

    PeopleSignInfoFragmentComponent addPeopleSignInfoFragment(PeopleSignInfoFragmentModule module);

    AccessFileComponent addAccessFile(AccessFileModule module);

   /* UpdatePeopleComponent addupdatepeople(UpdatePeopleModule module);*/

    //PeopleBaseCallComponent addPeopleBaseCallFragment(PeopleBaseInfoFragmentModule module);

    AddFindComponent addFind(AddFindModule addFindModule);

    DetailFindComponent detailFind(AddFindModule addFindModule);

    DetailFindStatusComponent detaildaibanFind(AddFindModule addFindModule);

    DeleteFindFragmentComponent addDeleteFindFragment(DeleteFindFragmentModule module);

    UrbanBaseInfoFragmentComponent addUrbanBaseInfoFragment(UrbanBaseInfoFragmentModule module);

    DetailFindDiscoverComponent detaildiscoverFind(AddFindModule addFindModule);

    HandleFindComponent handlenewFind(HandleFindModule handleFindModule);

    UrbanLowInsActivityComponent addUrbanLowInsActivity(UrbanLowInsActivityModule urbanLowInsActivityModule);

    UrbanLowAddFamilyActivityComponent addUrbanLowaddFamilyActivity(UrbanLowAddFamilyActivityModule urbanLowInsActivityModule);

    UrbanLowFamilyFragmentComponent addUrbanLowFamilyFragment(UrbanLowFamilyFragmentModule urbanLowInsActivityModule);

    UrbanLowFujianFragmentComponent addUrbanLowFUjianFragment(UrbanLowFamilyFragmentModule urbanLowInsActivityModule);

    UrbanLowAddFamilyBianjiActivityComponent addUrbanLowaddFamilybianjiActivity(UrbanLowAddFamilyActivityModule urbanLowInsActivityModule);

    UrbanBaseInfoBianjiFragmentComponent addUrbanBaseInfobianjiFragment(UrbanBaseInfoFragmentModule module);

    UrbanLowFamilyBianjiFragmentComponent addUrbanLowFamilybianjiFragment(UrbanLowFamilyFragmentModule urbanLowInsActivityModule);

    UrbanLowFujianBianjiFragmentComponent addUrbanLowFUjianbianjiFragment(UrbanLowFamilyFragmentModule urbanLowInsActivityModule);

    UrbanLowFujianBianjiNewFragmentComponent addUrbanLowFUjianbianjiNewFragment(UrbanLowFamilyFragmentModule urbanLowInsActivityModule);

    UrbanBaseInfoBianjiStatusFragmentComponent addUrbanBaseInfobianjistatusFragment(UrbanBaseInfoFragmentModule module);

    UrbanLowAddFamilyBianjiStatusActivityComponent addUrbanLowaddFamilybianjistatusActivity(UrbanLowAddFamilyActivityModule urbanLowInsActivityModule);

    IntroductionComponent addArea(SRCLoginModule loginModule);

    RoundFragmentComponent addRoundRoomFragment(RoundRoomFramentModule module);

    RoundFragmentNewComponent addRoundRoomnewFragment(RoundRoomFramentModule module);

    RoundDetailActivityComponent addRoundDetailActivity(RoundDetailActivityModule module);

    IndexElderFragmentComponent addIndexElderFragment(IndexElderFragmentModule indexFragmentModule);

    /**
     * 添加健康列表module
     * @param module
     * @return
     */
    HealthListFragmentComponent addHealthListFragment(HealthListFragmentModule module);

    /**
     * 添加病例记录列表module
     * @param module
     * @return
     */
    CaseListActivityComponent addCaseListFragment(CaseListFragmentModule module);
}
