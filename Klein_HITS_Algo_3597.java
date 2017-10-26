// SINGH AMBUJ  cs610 3597 PrP : Option 2 HITS Algorithms


import java.io.*;
import static java.lang.Math.*;
import java.util.*;

public class Klein_HITS_Algo_3597 {

        int iterations, initialvalue, nico, mich;
        String inputfile;
        final double err = 0.00001;
        int[][] AM;  
        double[] h0, a0;
         

   
        Klein_HITS_Algo_3597(int iterations, int initialvalue, String inputfile)     
    {
        this.iterations = iterations;
        this.initialvalue = initialvalue;
        this.inputfile = inputfile;
        try {        
            
        	File F = new File(inputfile);
        	Scanner sc = new Scanner(F);
           nico= sc.nextInt();
       //  System.out.println("Nicolas : "+nico);
            mich = sc.nextInt();
//          System.out.println("Michael : "+mich);    
            AM = new int[nico][nico];
            for(int i = 0; i < nico; i++)
             for(int j = 0; j < nico; j++)
               AM[i][j] = 0;

            while(sc.hasNextInt())
            {
                AM[sc.nextInt()][sc.nextInt()] = 1; 
           //     System.out.println(" Next Value is :: "+sc.nextInt());
            }
            
            h0 = new double[nico];
            a0 = new double[nico];
      
            
            
            if(initialvalue==0){
            	for(int i = 0; i < nico; i++) {
                    h0[i] = 0;
                    a0[i] = 0;
                  }
            }
            	else if(initialvalue==1){
            		for(int i = 0; i < nico; i++) {
                    h0[i] = 1;
                    a0[i] = 1;}	
            	}
            
            	else if(initialvalue==-1){
            		for(int i =0; i < nico; i++) {
                        h0[i] = 1.0/nico;
                        a0[i] = 1.0/nico;
                      }
            	}
            
            	else if(initialvalue==-2){
            		for(int i =0; i < nico; i++) {
                        h0[i] = 1.0/Math.sqrt(nico);
                        a0[i] = 1.0/Math.sqrt(nico);
                      }
            	}
            		else {}
            	
           sc.close();
        }
        catch(FileNotFoundException fileNotFound)
        {
        	
        }
    }

    public static void main(String[] args)
    {
       if(args.length != 3) {
            System.out.println("Please provide correct input");
            return;
        }
        
        
       int iterations = Integer.parseInt(args[0]);
        int initialvalue = Integer.parseInt(args[1]);
        String inputfile = args[2];
      
    
        if( !(initialvalue >= -2 && initialvalue <= 1) ) {
            System.out.println("Please provide correct InitialValue");
            return;
        }

        Klein_HITS_Algo_3597 hitobj = new Klein_HITS_Algo_3597(iterations, initialvalue, inputfile);

        hitobj.hitsImplement3597();
    }
 
    boolean checkconverged3597(double[] m, double[] n)
    {
       for(int i = 0 ; i < nico; i++) {
           if ( abs(m[i] - n[i]) > err ) 
             return false;
       }
       return true;
    } 
    
    public void hitsImplement3597()
    {
        double[] hub = new double[nico];
        double[] auth = new double[nico];
        double[] authprevious = new double[nico]; 
        double[] hubprevious = new double[nico]; 
        
        double authscaling = 0.0, authsum = 0.0 ;
        double hubscaling = 0.0, hubsum = 0.0;
        

        
        if(nico> 10) {
            iterations = 0;
        
            for(int i =0; i < nico; i++) {
            hub[i] = 1.0/nico;
            auth[i] = 1.0/nico;
              //  System.out.println(" Hub value : "+hub[i]);
             //   System.out.println(" auth value : "+auth[i]);
            hubprevious[i] = hub[i];
           authprevious[i] = auth[i];
            }
            
          int i = 0;
          do {  
            
        	 for(int c = 0; c < nico; c++) {
             authprevious[c] = auth[c];
             hubprevious[c] = hub[c];
            
            }
           
            
            // for auth
           for(int z = 0; z < nico; z++) {
        
        	 auth[z] = 0.0;
                }
            
            for(int m = 0; m < nico; m++) {
            for(int n = 0; n < nico; n++) {
            if(AM[n][m] == 1) {
              auth[m] = auth[m] + hub[n]; 
               }
              }
            }

            // for hub    
           for(int y = 0; y < nico; y++) {
            hub[y] = 0.0;
            }

            for(int r = 0; r < nico; r++) {
             for(int s = 0; s < nico; s++) {
             if(AM[r][s] == 1) {
             hub[r] = hub[r] + auth[s];
               //     System.out.println(" hub value : "+hub[r] ); 
                }
             }
             }                
             
            authscaling = 0.0;
            authsum = 0.0 ;
                
             for(int p = 0; p < nico; p++) {
             authsum = authsum + auth[p]*auth[p];  
              //     System.out.println(" auth sum : "+authsum ); 
                }
                
            authscaling = Math.sqrt(authsum); 
                
            for(int p = 0; p < nico; p++) {
            auth[p] = auth[p]/authscaling;
//          System.out.println(" iteration no : "+l );
  //        System.out.println(" auth value : "+auth[l] );
                } 
 
            hubscaling = 0.0;
            hubsum = 0.0;     
                         
            for(int p = 0; p < nico; p++) {
            hubsum = hubsum + hub[p]*hub[p]; 
//         System.out.println(" iteration no  : "+l );
//         System.out.println(" hub sum : "+hubsum );
                }
               
           hubscaling = Math.sqrt(hubsum); 
                
                
          for(int p = 0; p < nico; p++) {
          hub[p] = hub[p]/hubscaling;
         //   System.out.println(" iteration no : and hub value "+l+" "+hub[l] );
           }
                
                
          i++; 
                
                
          } while( false == checkconverged3597(auth, authprevious) || false == checkconverged3597(hub, hubprevious));
          System.out.println("iter:  " + i);
        //  System.out.println("This is iteration : "+i );
          
          
          for(int z = 0; z < nico; z++) {
              
        	
        	  System.out.printf(" A/H[%d]=%.6f/%.6f\n ",z,Math.round(auth[z]*1000000.0)/1000000.0,Math.round(hub[z]*1000000.0)/1000000.0); 
              
          }
          return;
        }

        
        for(int i = 0; i < nico; i++)
        {
         hub[i] = h0[i];
         auth[i] = a0[i];
         hubprevious[i] = hub[i];
         authprevious[i] = auth[i]; 
        }
        
        
        System.out.print("Base:  0 :");
     
        for(int p = 0; p < nico; p++) {
       //   System.out.println("This is iteration : "+p);
        System.out.printf(" A/H[%d]=%.6f/%.6f ",p,Math.round(a0[p]*1000000.0)/1000000.0,Math.round(h0[p]*1000000.0)/1000000.0); 
          
        }
        
        if (iterations != 0) { 
        for(int i = 0; i < iterations; i++) 
         { for(int p = 0; p < nico; p++) {
           auth[p] = 0.0;
          }
            
         for(int m = 0; m < nico; m++) {
         for(int n = 0; n < nico; n++) {
          if(AM[n][m] == 1) {
          auth[m] = auth[m] + hub[n]; 
           }
           }
          }

                
         for(int m = 0; m < nico; m++) {
         hub[m] = 0.0;
         }

          for(int m = 0; m < nico; m++) {
          for(int n = 0; n < nico; n++) {
          if(AM[m][n] == 1) {
          hub[m] = hub[m] + auth[n]; 
           }
          }
         }

               
         authscaling = 0.0;
         authsum = 0.0;
         for(int l = 0; l < nico; l++) {
         authsum = authsum + auth[l]*auth[l];    
         }
                
                
          authscaling = Math.sqrt(authsum); 
                
         for(int l = 0; l < nico; l++) {
         auth[l] = auth[l]/authscaling;
          } 
                 
         hubscaling = 0.0;
         hubsum = 0.0;
         for(int l = 0; l < nico; l++) {
         hubsum = hubsum + hub[l]*hub[l];    
          }
                
                
        hubscaling = Math.sqrt(hubsum); 
                
              
        for(int l = 0; l < nico; l++) {
         hub[l] = hub[l]/hubscaling;
         }
            
        System.out.println();
                
        System.out.print("iter:  " + (i+1) + " :");
             
         for(int z = 0; z < nico; z++) {
                	
     //   System.out.println("This is iteration : "+z);
          System.out.printf(" A/H[%d]=%.6f/%.6f ",z,Math.round(auth[z]*1000000.0)/1000000.0,Math.round(hub[z]*1000000.0)/1000000.0); 
       }
   
         }
        } 
        
        else
        {
        int i = 0;
        do {  
        for(int m = 0; m < nico; m++) {
           authprevious[m] = auth[m];
           hubprevious[m] = hub[m];
                }

               
         for(int p = 0; p < nico; p++) {
         auth[p] = 0.0;
         }
            
          for(int m = 0; m < nico; m++) {
          for(int n = 0; n < nico; n++) {
          if(AM[n][m] == 1) {
           auth[m] = auth[m] + hub[n]; 
           }
          }
          }

                
           for(int p = 0; p < nico; p++) {
           hub[p] = 0.0;
           }

            for(int m = 0; m < nico; m++) {
           for(int n = 0; n < nico; n++) {
            if(AM[m][n] == 1) {
            hub[m] = hub[m] + auth[n]; 
           }
           }
          }

               
            authscaling = 0.0;
            authsum = 0.0;
            
            for(int p = 0; p < nico; p++) {
           authsum = authsum + auth[p]*auth[p];    
            }
          
             authscaling = Math.sqrt(authsum); 
             
             for(int p = 0; p < nico; p++) {
              auth[p] = auth[p]/authscaling;
               }  
 
                
             hubscaling = 0.0;
             hubsum = 0.0;
             
             
             for(int p = 0; p < nico; p++) {
             hubsum = hubsum + hub[p]*hub[p];    
              }
            
             
             hubscaling = Math.sqrt(hubsum); 
             
             
             for(int p = 0; 0 < nico; p++) {
             hub[p] = hub[p]/hubscaling;
             }
             i++;
             
            
             
             System.out.println("iter:  " + i + " :");
              
                for(int z = 0; z < nico; z++) {
            //    	System.out.print("iter no is :  " + z);
                    System.out.printf(" A/H[%d]=%.6f/%.6f ",z, Math.round(auth[z]*1000000.0)/1000000.0, Math.round(hub[z]*1000000.0)/1000000.0); 
                }
          } while( false == checkconverged3597(auth, authprevious) || false == checkconverged3597(hub, hubprevious));
        }
       
    }
}

