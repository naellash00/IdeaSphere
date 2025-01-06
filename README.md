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
- 
  ---
### Links

- [Figma Design](https://www.figma.com/proto/oUBCUch383eDZlzbEHI1jv/IdeaSphere?node-id=61-497&p=f&t=1zzA4JYAwr813AdI-1&scaling=contain&content-scaling=fixed&page-id=0%3A1)
- [Presentation](https://www.canva.com/design/DAGbau1CiMA/fg470odHkUVnt0vgD1Unmg/edit)
- [PostMan API Documentation](https://documenter.getpostman.com/view/39709949/2sAYJAcwWX)
- 
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

### CompetitionController

1. **GET** `/get/recommend/competitions` - Recommend competitions to a participant based on their profile. *(Naelah)*
2. **PUT** `/add-review/{competition_id}` - Add a review for a specific competition. *(Naelah)*
3. **GET** `/company/get/my-competition/reviews/{competition_id}` - Get reviews for a competition organized by a company. *(Naelah)*
4. **GET** `/individual/get/my-competition/reviews/{competition_id}` - Get reviews for a competition organized by an individual. *(Naelah)*

**Total**: 4 endpoints

---

### ParticipantController

1. **GET** `/get/my-achievements` - Retrieve achievements of the authenticated participant. *(Naelah)*
2. **GET** `/get/my-feedbacks` - Retrieve feedbacks for the authenticated participant. *(Naelah)*
3. **POST** `/send-complain` - Submit a complaint by a participant. *(Naelah)*

**Total**: 3 endpoints

---

### SubmissionController

1. **POST** `/request-feedback/{submission_id}` - Request feedback on a specific submission. *(Naelah)*
2. **PUT** `/company/accept/feedback/request/{submission_id}` - Company organizer accepts a feedback request. *(Naelah)*
3. **PUT** `/company/reject/feedback/request/{submission_id}` - Company organizer rejects a feedback request. *(Naelah)*
4. **PUT** `/individual/accept/feedback/request/{submission_id}` - Individual organizer accepts a feedback request. *(Naelah)*
5. **PUT** `/individual/reject/feedback/request/{submission_id}` - Individual organizer rejects a feedback request. *(Naelah)*
6. **PUT** `/company/select/winner/{competition_id}/{submission_id}` - Company organizer selects a competition winner. *(Naelah)*
7. **PUT** `/individual/select/winner/{competition_id}/{submission_id}` - Individual organizer selects a competition winner. *(Naelah)*
8. **GET** `/company/view/my-competition/submissions/{competition_id}` - Company organizer views submissions for their competition. *(Naelah)*
9. **GET** `/individual/view/my-competition/submissions/{competition_id}` - Individual organizer views submissions for their competition. *(Naelah)*

**Total**: 9 endpoints

---

### VoteController

1. **PUT** `/vote/{submission_id}` - Participant casts a vote on a specific submission. *(Naelah)*

**Total**: 1 endpoint

---

### Summary

- **CompetitionController**: 4 endpoints
- **ParticipantController**: 3 endpoints
- **SubmissionController**: 9 endpoints
- **VoteController**: 1 endpoint

**Grand Total (API Endpoints)**: 17
**Controllers**: 4
