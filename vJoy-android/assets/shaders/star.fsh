#ifdef GL_ES
precision highp float;
#endif

uniform float time;
uniform vec2 resolution;
uniform sampler2D uTexture;

void main(void)
{
    vec2 uv;

    vec2 p = -1.0 + 2.0 * gl_FragCoord.xy / resolution.xy;
    float a = atan(p.y, p.x);
    float r = sqrt(dot(p, p));
    float s = r * (1.0 + 0.8 * cos(time * 1.0));

    uv.x = 0.02 * p.y + 0.03 * cos(-time + a * 3.0) / s;
    uv.y = 0.1 * time + 0.02 * p.x + 0.03 * sin(-time + a * 3.0) / s;

    float w = 0.9 + pow(max(1.5 - r, 0.0), 4.0);

    w *= 0.6 + 0.4 * cos(time + 3.0 * a);

    vec3 col = texture2D(uTexture, uv).xyz;

    gl_FragColor = vec4(col * w, 1.0);
}
    