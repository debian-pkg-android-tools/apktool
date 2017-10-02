/**
 *  Copyright (C) 2017 Ryszard Wiśniewski <brut.alll@gmail.com>
 *  Copyright (C) 2017 Connor Tumbleson <connor.tumbleson@gmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package brut.androlib;

import brut.common.BrutException;
import brut.directory.ExtFile;
import brut.util.OS;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Connor Tumbleson <connor.tumbleson@gmail.com>
 */
public class OutsideOfDirectoryEntryTest {

    @BeforeClass
    public static void beforeClass() throws Exception {
        TestUtils.cleanFrameworkFile();
        sTmpDir = new ExtFile(OS.createTempDirectory());
        TestUtils.copyResourceDir(DecodeKotlinTest.class, "brut/apktool/issue1589/", sTmpDir);

        String apk = "issue1589.apk";

        // decode issue1589.apk
        ApkDecoder apkDecoder = new ApkDecoder(new File(sTmpDir + File.separator + apk));
        sTestNewDir = new ExtFile(sTmpDir + File.separator + apk + ".out");

        apkDecoder.setOutDir(new File(sTmpDir + File.separator + apk + ".out"));
        apkDecoder.decode();
    }

    @AfterClass
    public static void afterClass() throws BrutException {
        OS.rmdir(sTmpDir);
    }

    @Test
    public void skippedDecodingOfInvalidFileTest() throws BrutException {
        assertTrue(sTestNewDir.isDirectory());

        File testAssetFolder = new File(sTestNewDir, "assets");
        assertFalse(testAssetFolder.isDirectory());
    }


    private static ExtFile sTmpDir;
    private static ExtFile sTestNewDir;

    private final static Logger LOGGER = Logger.getLogger(OutsideOfDirectoryEntryTest.class.getName());
}