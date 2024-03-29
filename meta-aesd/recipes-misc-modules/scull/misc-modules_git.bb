# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Mo2meN-Ali.git;protocol=ssh;branch=master \
            file://makefile-ldd.patch \
            file://misc-modules.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "5c976f661230d8c393b07d943b74209b6bcf1e54"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# Updated for misc-modules
FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"
FILES:${PN} += "${sysconfdir}/init.d/misc-modules.sh"

inherit update-rc.d
INITSCRIPT_NAME = "misc-modules.sh"
INITSCRIPT_PARAMS = "start 98 5 2 . stop 20 0 1 6 ."

do_install () {
	bbwarn "misc-modules recipe"

# Installating the loading and unloading scripts into /bin
	install -d ${D}${bindir}
	install -m 0755 ${S}/misc-modules/module_load ${D}${bindir}
	install -m 0755 ${S}/misc-modules/module_unload ${D}${bindir}

# start-stop directory & script installation into /etc/init.d	
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/misc-modules.sh ${D}${sysconfdir}/init.d

# Installing the module faulty.ko & hello.ko in the 5.15.124-yocto-standard directory
	install -d ${D}${base_libdir}/modules/5.15.124-yocto-standard/extra
	install -m 0755 ${S}/misc-modules/faulty.ko ${D}${base_libdir}/modules/5.15.124-yocto-standard/extra
	install -m 0755 ${S}/misc-modules/hello.ko ${D}${base_libdir}/modules/5.15.124-yocto-standard/extra
}