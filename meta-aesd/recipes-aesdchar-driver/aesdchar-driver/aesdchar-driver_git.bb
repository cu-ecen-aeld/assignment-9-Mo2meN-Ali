# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "gitsm://github.com/cu-ecen-aeld/assignments-3-and-later-Mo2meN-Ali;protocol=https;branch=master \
           file://001-Makefile.patch"

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "fc7dd3eef604d0e24ce176f5e85d5011d3c55aee"

S = "${WORKDIR}/git/aesd-char-driver"

inherit module
RPROVIDES:${PN} += "kernel-module-aesdchar"

inherit update-rc.d
INITSCRIPT_NAME   = "aesdchar-start-stop.sh"
INITSCRIPT_PARAMS = "start 96 5 2 . stop 0 1 6 ." 

FILES:${PN} += "${sysconfdir}/init.d/aesdchar-start-stop.sh"
FILES:${PN} += "${bindir}/aesdchar_load"
FILES:${PN} += "${bindir}/aesdchar_unload"

do_install() {
        bbwarn "aesdchar device driver recipe"
        install -d ${D}${bindir}
        install -m 0755 ${S}/aesdchar_load ${D}${bindir}
        install -m 0755 ${S}/aesdchar_unload ${D}${bindir}

        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${S}/aesdchar-start-stop.sh ${D}${sysconfdir}/init.d

        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
        install -m 0755 ${S}/aesdchar.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/extra
}
