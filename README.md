🌱 One Voice – Community Support & Complaint Management Platform

One Voice is a full-stack community engagement platform designed to bridge the communication gap between villagers (Individuals) and Panchayath authorities.
It provides a smooth workflow for complaint registration, event/news posting, email notifications, AI assistant support, and role-based access control.

🚀 Features
👤 Role-Based Login
Individual Login
Panchayath Login
Secured using Spring Security
Custom UserDetailsService + PasswordEncoder

🔐 OTP-Based Account Creation
🔸 Individual Registration
Email-based OTP verification
User profile stored after successful OTP validation
🔸 Panchayath Registration
Validated using OneVoice Panchayath ID
Email-based OTP verification
Only authorized Panchayaths can register

📝 Complaint Management System (Individuals)
Register complaints with:
Issue details
Village/area
Attached image/video (stored locally)
Auto-captured latitude & longitude using HTML5 Geolocation
Track complaint status
View all submitted cases

🏢 Panchayath Dashboard
View all complaints raised by citizens
Update complaint status (Pending → In Progress → Resolved)
View complaint location on map
Send email reply to users directly from dashboard
Post events and news

📰 Events & News
Both Individuals and Panchayaths can:
Post events
View events
Events stored and fetched through database

🤖 Talk With AI (Chiru – Local LLM Assistant)
Powered by Ollama + Gemma model running locally
Integrated using Spring WebClient
Helps users with:
Complaint status queries
Event/news information
“How to” questions
AI stays strictly One Voice–context aware

🏗️ Architecture

The project follows clean layered architecture:
Controller → Service → Repository → Database

Technologies used:
Java 17
Spring Boot
Spring Security
Spring Data JPA (Hibernate)
PostgreSQL
HTML, CSS, JavaScript, Thymeleaf
Ollama (Local LLM)
WebClient (AI API integration)
Java Mail Sender (OTP + Notifications)

📂 Project Modules
1️⃣ Authentication & Security
Login
OTP verification
Role-based routing
SecurityContext + Principal for identity fetching

2️⃣ Complaint Module
Case registration
Upload image/video
Fetch user-specific complaints
Panchayath view + resolve

3️⃣ Event/News Module
Post events
View all events
Author identification with principal.getName()

4️⃣ AI Assistant Module
Intent-based query handling
DB-backed responses
Fallback to LLM
WebClient → Local Ollama API

📦 Folder Structure
src/
 ├── main/java/com/onevoice/management/onevoice
 │    ├── controllers
 │    ├── service
 │    ├── repository
 │    ├── model
 │    ├── config
 │    └── security
 └── resources
      ├── templates (HTML/Thymeleaf)
      ├── static (CSS, JS, images)
      └── application.properties

🛠️ Setup & Run
1️⃣ Clone the project
git clone https://github.com/your-username/one-voice.git
cd one-voice

2️⃣ Configure PostgreSQL

Update application.properties with your DB credentials.

3️⃣ Run the Application

Inside IntelliJ:

Run → OneVoiceApplication

4️⃣ Start Ollama Local Model
ollama run gemma3:1b

5️⃣ Open in Browser
http://localhost:8080
