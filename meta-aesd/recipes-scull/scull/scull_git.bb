# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-Mo2meN-Ali.git;protocol=ssh;branch=master \
            file://makefile-ldd.patch \
            file://scullmodules.sh"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "5c976f661230d8c393b07d943b74209b6bcf1e54"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

# Updated for scull
FILES:${PN} += "${bindir}/scull_load"
FILES:${PN} += "${bindir}/scull_unload"
FILES:${PN} += "${sysconfdir}/init.d/scullmodules.sh"

inherit update-rc.d
INITSCRIPT_NAME = "scullmodules.sh"
INITSCRIPT_PARAMS = "start 97 5 2 . stop 20 0 1 6 ."

do_install () {
	bbwarn "Scull modules recipe"

# Installating the loading and unloading scripts into /bin
	install -d ${D}${bindir}
	install -m 0755 ${S}/scull/scull_load ${D}${bindir}
	install -m 0755 ${S}/scull/scull_unload ${D}${bindir}

# start-stop directory & script installation into /etc/init.d	
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/scullmodules.sh ${D}${sysconfdir}/init.d

# Installing the module scull.ko in the 5.15.124-yocto-standard directory
	install -d ${D}${base_libdir}/modules/5.15.124-yocto-standard/extra
	install -m 0755 ${S}/scull/scull.ko ${D}${base_libdir}/modules/5.15.124-yocto-standard/extra
}