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

**الوصف**

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

## My Work on the Project

My work: **Category**, **Competition**, **Participant**, **Submission**, and **Vote** models, including their respective CRUD operations. 
### Extra Endpoints

1. **POST** `/register` - Register a new participant. *(ParticipantController)*
2. **POST** `/submit/{competition_id}` - Submit an entry for a competition. *(SubmissionController)*
3. **PUT** `/vote/{submission_id}` - Cast a vote on a submission. *(VoteController)*
4. **GET** `/get/my-submissions` - Retrieve all submissions of the participant. *(SubmissionController)*
5. **PUT** `/add/category/to/participant/{category_id}` - Add a category to the participant's profile. *(CategoryController)*
6. **GET** `/get/my-achievements` - Retrieve achievements of the participant. *(ParticipantController)*
7. **PUT** `/company/select/winner/{competition_id}/{submission_id}` - Select a winner for a competition as a company. *(SubmissionController)*
8. **PUT** `/individual/select/winner/{competition_id}/{submission_id}` - Select a winner for a competition as an individual. *(SubmissionController)*
9. **GET** `/get/recommend/competitions` - Get competition recommendations for the participants based on their categories. *(CompetitionController)*
10. **POST** `/request-feedback/{submission_id}` - Request feedback for a submission. *(SubmissionController)*
11. **PUT** `/company/accept/feedback/request/{submission_id}` - Accept a feedback request as a company. *(SubmissionController)*
12. **PUT** `/company/reject/feedback/request/{submission_id}` - Reject a feedback request as a company. *(SubmissionController)*
13. **PUT** `/individual/accept/feedback/request/{submission_id}` - Accept a feedback request as an individual. *(SubmissionController)*
14. **PUT** `/individual/reject/feedback/request/{submission_id}` - Reject a feedback request as an individual. *(SubmissionController)*
15. **PUT** `/add-review/{competition_id}` - Add a review to a competition. *(CompetitionController)*
16. **GET** `/get/my-feedbacks` - Retrieve feedbacks for the participant. *(ParticipantController)*
17. **GET** `/company/get/my-competition/reviews/{competition_id}` - Retrieve reviews for a company's competition. *(CompetitionController)*
18. **GET** `/individual/get/my-competition/reviews/{competition_id}` - Retrieve reviews for an individual's competition. *(CompetitionController)*
19. **POST** `/send-complain` - Send a complaint or inquiry. *(ParticipantController)*
20. **GET** `/company/view/my-competition/submissions/{competition_id}` - View submissions for a company's competition. *(SubmissionController)*
21. **GET** `/individual/view/my-competition/submissions/{competition_id}` - View submissions for an individual's competition. *(SubmissionController)*

---

### Controllers:

1. **CompetitionController**
2. **ParticipantController**
3. **SubmissionController**
4. **VoteController**

### DTO:

1. **ParticipantInDTO**
2. **AchievementOutDTO**
3. **CategoryOutDTO**
4. **FeedbackOutDTO**
5. **ParticipantOutDTO**
6. **SubmissionOutDTO**

### Models:

1. **MyUser**
2. **Participant**
3. **Submission**
4. **Vote**
5. **WinnerPayment**

### Repository:

1. **AuthRepository**
2. **ParticipantRepository**
3. **SubmissionRepository**
4. **VoteRepository**
5. **ParticipantOutDTO**
6. **SubmissionOutDTO**

### Service:

1. **AdminService**
2. **AuthService**
3. **ParticipantService**
4. **SubmissionService**
5. **VoteService**
6. **WinnerPaymentService**




