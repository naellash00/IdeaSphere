# IdeaSphere API Documentation

**IdeaSphere System Description**

IdeaSphere is a dedicated platform that empowers individuals to showcase their creativity and innovation through organizing and participating in competitions across various fields. Organizers can be individuals or companies.

### Features

- **Competition Creation**: Enables organizers to create and manage competitions designed to meet their goals and requirements.
- **Submission Management**: Allows participants to submit their work easily, and organizers to review and evaluate entries.
- **Voting System**: Provides a fair and clear way to determine winners through public votes or organizer selection.
- **Notifications**: Keeps users informed with updates on deadlines, announcements, and competition results.
- **Diverse Categories**: Supports a wide range of competition types to suit different skills and interests.

---

**المقدمة**

فضاء الحلول هي منصة مخصصة تتيح للأفراد استعراض إبداعاتهم وابتكاراتهم من خلال تنظيم والمشاركة في مسابقات في مختلف المجالات. يمكن أن يكون المنظمون أفرادًا أو شركات.

### الميزات

- **إنشاء المسابقات**: تتيح للمنظمين والافراد إنشاء وإدارة مسابقات مصممة لتتناسب مع أهدافهم واحتياجاتهم.
- **إدارة المشاركات**: يمكن للمشاركين تقديم أعمالهم بسهولة، وللمنظمين مراجعة وتقييم المشاركات.
- **نظام التصويت**: يوفر طريقة عادلة وواضحة لتحديد الفائزين من خلال تصويت الجمهور أو اختيار المنظم.
- **الإشعارات**: تبقي المستخدمين على اطلاع بآخر التحديثات والمواعيد النهائية وإعلانات النتائج.
- **فئات متنوعة**: تدعم مجموعة واسعة من أنواع المسابقات لتلبي مختلف المهارات والاهتمامات.
  ---
### Links

- [Figma Design](https://www.figma.com/proto/oUBCUch383eDZlzbEHI1jv/IdeaSphere?node-id=61-497&p=f&t=1zzA4JYAwr813AdI-1&scaling=contain&content-scaling=fixed&page-id=0%3A1)
- [Presentation](https://www.canva.com/design/DAGbau1CiMA/fg470odHkUVnt0vgD1Unmg/edit)
- [PostMan API Documentation](https://documenter.getpostman.com/view/39709949/2sAYJAcwWX)
  ---

## Class Diagram

![Class Diagram](https://cdn.discordapp.com/attachments/1321830373256335403/1325847348890566749/IdeaSphereClassDiagram.drawio.png?ex=677d4711&is=677bf591&hm=544b66b3840b4305752da97ce688d9c900d7666a08bf0c9d47adc8376e722fe1&)

## Use Case Diagram

![Use Case Diagram](https://cdn.discordapp.com/attachments/1321830373256335403/1325964561186164766/ideaSphereUseCase.drawio.png?ex=677db43a&is=677c62ba&hm=db0d9c750d1284664d39951e937a190b0a289b3b464040db2fb2f33d58abb08a&)

---

## Summary

- **Total Endpoints**: 41
- **Controllers**: 8

---

## Endpoints by Controller

### CompanyCompetitionController

1. **GET** `/get-all-competition` - Get all competitions for Company Competition.
2. **GET** `/get-my-competitions` - Get competitions for the authenticated company organizer.
3. **POST** `/create-competition-financial-interview` - Create a competition with financial and interview reward types.
4. **POST** `/create-competition-interview` - Create a competition with an interview reward type.
5. **POST** `/create-competition-financial-by-vote` - Create a competition with a financial reward and winner selection by vote.
6. **POST** `/create-competition-financial-by-organizer` - Create a competition with a financial reward and winner selection by the organizer.
7. **POST** `/add-competition-payment` - Pay monetary reward to start the competition.
8. **PUT** `/extend-competition` - Extend the competition dates and participant numbers.
9. **PUT** `/update-competition` - Update competition details.
10. **PUT** `/cancel-competition/{companyCompetitionId}` - Cancel a competition and return payment if applicable.

**Total**: 10 endpoints

### CompanyOrganizerController

1. **GET** `/get-profile` - Get the profile of the authenticated company organizer.
2. **POST** `/register` - Register as a company organizer.
3. **PUT** `/update-profile` - Update the profile of the authenticated company organizer.

**Total**: 3 endpoints

### CompetitionPaymentController

1. **GET** `/get-my-competition-payment` - Get all competition payments for the authenticated organizer.

**Total**: 1 endpoint

### MonthlySubscriptionController

1. **GET** `/get-all-monthly-subscription` - Get all monthly subscriptions (Admin only).
2. **GET** `/get-my-monthly-subscription` - Get the monthly subscriptions of the authenticated user.
3. **POST** `/subscribe/{subscriptionPackageId}` - Subscribe to a package.
4. **POST** `/renew-subscription/{subscriptionPackageId}` - Renew an existing subscription.

**Total**: 4 endpoints

### SubscriptionPackageController

1. **GET** `/get-all-subscription-package` - Get all subscription packages.
2. **GET** `/get-subscription-package-by-id/{id}` - Get a subscription package by its ID.
3. **POST** `/add-subscription-package` - Add a new subscription package (Admin only).
4. **PUT** `/update-subscription-package` - Update a subscription package (Admin only).
5. **PUT** `/active-subscription-package/{id}` - Activate a subscription package (Admin only).
6. **PUT** `/deactivated-subscription-package/{id}` - Deactivate a subscription package (Admin only).

**Total**: 6 endpoints

### AdminController

1. **PUT** `/active-company-user/{id}` - Activate a company account after reviewing its information.
2. **PUT** `/detective-company-user/{id}` - Deactivate a company account.

**Total**: 2 endpoints

### SchedulerService

1. Automatically scheduled task: `updateExpiredCompetition` - Updates competitions that have expired.
2. Automatically scheduled task: `updateStuckCompetition` - Updates competitions stuck in an unresolved state.
3. Automatically scheduled task: `updateCompetitionUnderVote` - Updates competitions under public voting.

**Total**: 3 tasks (automated, not API endpoints)

---

**Grand Total (API Endpoints)**: 41



