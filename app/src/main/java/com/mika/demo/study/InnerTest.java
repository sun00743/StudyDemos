package com.mika.demo.study;

/**
 * Created by mika on 2020/2/21.
 */
public class InnerTest {

    class OutClass {

        public int outValue;

        private void outTest() {

        }

        abstract class InClass {

            int value;

            abstract void test();
        }

    }

    class Demo {

        int demoValue;

        public void function() {
            new OutClass().new InClass(){
                @Override
                void test() {

                }
            };
        }
    }

}
