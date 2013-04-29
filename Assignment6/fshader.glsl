// Light color
uniform vec4 lightColor;

// Light position
uniform vec4 lightPosition;

// Diffuse reflection color
uniform vec4 diffuseColor;

// Specular reflection color
uniform vec4 specColor;

// Specular exponent
uniform float specExp;

// Specular exponent
uniform vec3 pos;

// Vectors "attached" to vertex and get sent to fragment shader
varying vec3 lPos;
varying vec3 vPos;
varying vec3 vNorm;


void main()
{        
    // calculate your vectors
    vec3 L = normalize (lPos - vPos);
    vec3 N = normalize (vNorm);
    
    vec3 R = (float(2) * N * (dot(N,L))) - L;
    vec3 V = normalize (pos - vPos);
    
     // calculate components
    vec4 diffuse = lightColor * diffuseColor * (dot(N, L));
    
    vec4 specular = lightColor * specColor * (pow((dot(R, V)),specExp));
    
    // set the final color
    gl_FragColor = diffuse + specular;

}
