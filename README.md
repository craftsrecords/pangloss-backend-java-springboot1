## DISCLAIMER

This application has been made to demonstrate security flaws and possible mitigation.
The safer mode is not fully secured and not suitable for a production usage.
Copy & Paste code at your own risk.

# OWASP Pangloss

OWASP Pangloss is a [OWASP TOP10 2017](https://www.owasp.org/index.php/Top_10-2017_Top_10) sandbox made to help people learn the common security flaws of applications.

The name is inspired by the Pangloss character of Voltaire's novella [Candide](https://en.wikipedia.org/wiki/Candide). 
Pangloss is Candide's teacher, and follows a Leibnizian mantra: "all is for the best" in the "best of all possible worlds".

## Building the backend
`mvn clean install`

## Run Pangloss

Several profiles are available to run Pangloss, here is the list:

* insecure - enable all the security flaws.
* mitigated - enable almost all the mitigation implemented in the app without ssl and hashedPasswords.
* ssl - enable ssl (badly named in fact TLS) for Pangloss.
* hashedPasswords - enable the storage of hashed user passwords.
* safer - combination of mitigated, ssl, hashedPasswords.

To run Pangloss, select the profiles you wants and use this command line:
```
java -jar target/pangloss-backend-java-springboot1-0.0.1-SNAPSHOT.jar --spring.profiles.active=profile1,profile2
```

The application will be bound to localhost:1759.

**Note:** insecure is not compatible with mitigated and safer.

You should run as well run the [frontend part](https://gitlab.com/crafts-records/pangloss/pangloss-frontend-angular).