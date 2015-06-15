/*
 * The MIT License
 *
 * Copyright 2015 ltw578.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package magpie.attributes.generators;

import magpie.data.materials.CompositionDataset;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Logan Ward
 */
public class IonicityAttributeGeneratorTest {

    @Test
    public void test() throws Exception {
        // Make dataset
        CompositionDataset data = new CompositionDataset();
        data.addEntry("NaCl");
        data.addEntry("Al2MnCu");
        data.addEntry("Fe");
        
        // Make generator
        IonicityAttributeGenerator gen = new IonicityAttributeGenerator();
        
        // Run generator
        gen.addAttributes(data);
        
        // Test results
        assertEquals(3, data.NAttributes());
        assertEquals(3, data.getEntry(0).NAttributes());
        
        // Results for NaCl
        assertEquals(data.getAttributeName(0),      1, data.getEntry(0).getAttribute(0), 1e-6);
        assertEquals(data.getAttributeName(1), 0.7115, data.getEntry(0).getAttribute(1), 1e-2);
        assertEquals(data.getAttributeName(2), 0.3557, data.getEntry(0).getAttribute(2), 1e-2);
        
        // Results for Al2MnCu
        assertEquals(data.getAttributeName(0),      0, data.getEntry(1).getAttribute(0), 1e-6);
        assertEquals(data.getAttributeName(1), 0.0301, data.getEntry(1).getAttribute(1), 1e-2);
        assertEquals(data.getAttributeName(2), 0.0092, data.getEntry(1).getAttribute(2), 1e-2);
        
        // Results for Fe
        assertEquals(data.getAttributeName(0), 0, data.getEntry(2).getAttribute(0), 1e-6);
        assertEquals(data.getAttributeName(1), 0, data.getEntry(2).getAttribute(1), 1e-2);
        assertEquals(data.getAttributeName(2), 0, data.getEntry(2).getAttribute(2), 1e-2);
    }
    
}