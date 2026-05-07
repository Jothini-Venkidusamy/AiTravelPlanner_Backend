$env:MONGODB_URI = "mongodb+srv://jothiniv2_db_user:joo123@cluster0.ptn4sni.mongodb.net/travelplanner?appName=Cluster0"
$env:JWT_SECRET = "local-development-secret-key-for-ai-travel-planner-2026"

Set-Location "$PSScriptRoot\.."
& ".\.tools\apache-maven-3.9.9\bin\mvn.cmd" `
  "-Dmaven.repo.local=$PWD\.m2\repository" `
  -f backend\pom.xml `
  spring-boot:run
