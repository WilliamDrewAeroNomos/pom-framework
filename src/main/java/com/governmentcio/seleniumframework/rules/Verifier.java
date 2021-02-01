package com.governmentcio.seleniumframework.rules;

import java.util.Arrays;
import java.util.Map;

import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import com.governmentcio.seleniumframework.rules.TestCaseRecorder;


public class Verifier extends Assertion {
		  private final Map<AssertionError, IAssert<?>> m_errors = Maps.newLinkedHashMap();
		  private TestCaseRecorder tr;
		  
		  public Verifier(TestCaseRecorder tr){
			  this.tr = tr;
		  }
		  
		    @Override
		    protected void doAssert(IAssert<?> a) {
		        onBeforeAssert(a);
		        
		        try {
		            a.doAssert();
		            onAssertSuccess(a);		
		            
		            tr.success(a.getMessage());
		        } catch (AssertionError ex) {
		        	tr.fail(System.lineSeparator() + System.lineSeparator() + ex + System.lineSeparator() + System.lineSeparator());
		            onAssertFailure(a, ex);
		            m_errors.put(ex, a);
		        } finally {
		            onAfterAssert(a);
		        }
		    }

		    public void assertAll() {
		        if (! m_errors.isEmpty()) {
		            StringBuilder sb = new StringBuilder("The following asserts failed:");
		            boolean first = true;
		            for (Map.Entry<AssertionError, IAssert<?>> ae : m_errors.entrySet()) {
		                if (first) {
		                    first = false;
		                } else {
		                    sb.append(",");
		                }
		                sb.append("\n\t");
		                sb.append(ae.getKey().getMessage());
		                sb.append("\nStack Trace :");
		                sb.append(Arrays.toString(ae.getKey().getStackTrace()).replaceAll(",", "\n"));
		            }
		            //tr.fail("Erros: " + System.lineSeparator() + System.lineSeparator()  + sb.toString() + System.lineSeparator() + System.lineSeparator() );
		            throw new AssertionError(sb.toString());
		        }
		    }

}