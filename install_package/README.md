## Installation package with production version of OST.

To run production version of OST just run `docker-compose up -d` in install_package directory.

SSL certificate (certificate and key) is located in `certificates` directory. The default certificate which
came with the package should be swapped with valid one before running this package on production.

After all docker containers successfully start application should be accessible on localhost and outside the server it
is running on. There is redirection from port 80 to 433 by default, which is desirable because ost application doesn't
fully function without https. If for some reason there is a need to change the routing settings edit
`/etc/nginx/conf.d/itti-app.conf` file in ost_web docker container.

After first installation default admin account is accessible with credentials:

admin

admin

It is recommended to run this package locally, change admin user's password and then copy this package
(or just `postgresql` directory) to production environment. While editing admin user it is required to fill
First Name, Last Name, email and Contact(Phone) fields. Before changing users password these fields should be edited and
saved, otherwise password will not be changed.

If right after staring package login attempts are meet with pop-up "You cannot log in while being offline", it means
that backend is still not available and needs more time to set up database, mappings and kafka topics listeners.

