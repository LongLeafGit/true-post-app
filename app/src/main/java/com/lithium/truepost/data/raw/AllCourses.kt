package com.lithium.truepost.data.raw

import com.lithium.truepost.R
import com.lithium.truepost.data.model.CourseContent
import com.lithium.truepost.data.model.CourseData

val AllCourses = listOf(
    CourseData(
        id = 0,
        imageResId = R.drawable.bot,
        title = "¿Qué son los bots sociales?",
        description = "Aprende que son los bots y por qué son sociales.",
        content = listOf(
            CourseContent(
                title = "¿Qué son los bots sociales?",
                text = "Un bot es un programa automatizado que simula ser una persona en redes sociales.",
                imageResId = R.drawable.bot1,
            ),
            CourseContent(
                title = "¿Cómo se comporta un bot?",
                text = "No tiene emociones reales, solo repite lo que le programaron.\n\nNo responde de forma natural si le haces preguntas abiertas.\n\nA veces comete errores raros o responde cosas sin sentido.",
                imageResId = R.drawable.bot2,
            ),
            CourseContent(
                title = "Cómo identificar un bot",
                text = "Nombre de usuario extraño o genérico.\n\nFoto de perfil falsa o sin rostro humano.\n\nPublica a todas horas, incluso de madrugada.\n\nRepite frases que también usan otras cuentas.\n\nSolo habla de un tema con intensidad exagerada.",
                imageResId = R.drawable.bot3,
            ),
            CourseContent(
                title = "¿Para qué se usan los bots?",
                text = "Para hacer propaganda política o comercial.\n\nPara confundir o dividir a la gente.\n\nPara inflar la fama a alguien artificialmente.",
                imageResId = R.drawable.bot4,
            ),
        ),
    ),



    CourseData(
        id = 1,
        imageResId = R.drawable.fact_check,
        title = "Tipos de manipulación de información en redes sociales",
        description = "Conoce las formas en que un bot puede manipular la información.",
        content = listOf(
            CourseContent(
                title = "Desinformación",
                text = "Contenido falso creado para engañar, como noticias inventadas o fotos alteradas.",
                imageResId = R.drawable.desinformacion,
            ),
            CourseContent(
                title = "Información sesgada",
                text = "Solo se muestra una parte de la verdad para apoyar una idea o causar confusión.",
                imageResId = R.drawable.informacion_sesgada,
            ),
            CourseContent(
                title = "Contenido fuera de contexto",
                text = "Se usa una imagen o frase real, pero se presenta en una situación distinta a la original.",
                imageResId = R.drawable.contenido_fuera_de_contexto,
            ),
            CourseContent(
                title = "Astroturfing",
                text = "Varios bots o cuentas falsas actúan como si muchas personas apoyaran una idea.",
                imageResId = R.drawable.astroturfing,
            ),
            CourseContent(
                title = "Deepfakes",
                text = "Videos o audios falsos creados con inteligencia artifical, donde alguien dice o hace algo que nunca ocurrió.",
                imageResId = R.drawable.deepfakes,
            ),
            CourseContent(
                title = "Flooding",
                text = "Se inunda una red social con mensajes repetidos para ocultar otras opiniones o noticias reales.",
                imageResId = R.drawable.flooding,
            ),
            CourseContent(
                title = "Fake influencers",
                text = "Cuentas que aparentan ser populares pero son infladas con bots y seguidores comprados.",
                imageResId = R.drawable.fake_influencers,
            ),
        )
    ),



    CourseData(
        id = 2,
        imageResId = R.drawable.posts,
        title = "¿Cómo identificar publicaciones manipuladas y legitimas?",
        description = "Empieza a diferenciar publicaciones en redes sociales.",
        content = listOf(
            CourseContent(
                title = "Las publicaciones legitimas suelen:",
                text = "1. Incluir fuentes confiables y enlaces verificables.\n2. Tener un tono equilibrado, sin exageraciones.\n3. Estar bien redactadas, con coherencia y sin errores extraños.\n4. Mostrar opiniones con argumentos claros y razonables.\n5. Ser compartidas por usuarios reales con historial auténtico.\n6. Estar acompañadas de comentarios diversos y naturales.\n7. Mostrar imágenes coherentes y bien contextualizadas.\n8. Tener una fecha de publicación clara y reciente.\n9. Aceptar diferentes puntos de vista en los comentarios.\n10. No presionar ni imponer una emoción o decisión.",
            ),
            CourseContent(
                title = "Las publicaciones manipuladas suelen:",
                text = "1. Usar titulares sensacionalistas o alarmistas.\n2. No citar fuentes o citar páginas poco confiables.\n3. Repetir el mismo mensaje en muchas cuentas distintas.\n4. Tener errores gramaticales o frases robóticas.\n5. Buscar provocar odio, miedo o enojo inmediato.\n6. Usar imágenes fuera de contexto o alteradas digitalmente.\n7. Estar publicadas por cuentas nuevas o sospechosas.\n8. Tener likes altos pero comentarios vacíos o repetidos.\n9. Compartirse en masa por bots o cuentas sin foto real.\n10. Evitar el debate o bloquear opiniones contrarias.",
            ),
        )
    ),



    CourseData(
        id = 3,
        imageResId = R.drawable.disinformation,
        title = "El impacto de la desinformación en la sociedad digital",
        description = "Estudia cómo nos afecta la desinformación en nuestra vida diaria.",
        content = listOf(
            CourseContent(
                title = "Erosiona la confianza en instituciones",
                text = "La repetida exposición a noticias falsas debilita la credibilidad de medios, gobiernos, ciencia y sistemas judiciales.",
            ),
            CourseContent(
                title = "Promueve la polarización social",
                text = "La desinformación refuerza burbujas ideológicas y fomenta el conflicto entre grupos con diferentes opiniones.",
            ),
            CourseContent(
                title = "Aprovecha algoritmos de recomendación",
                text = "Plataformas digitales tienden a mostrar contenido sensacionalista o extremo porque genera más interacción, lo cual favorece la viralización de mentiras.",
            ),
            CourseContent(
                title = "Ataca a minorías y fomenta discursos de odio",
                text = "Muchas campañas de desinformación difunden estereotipos, teorías conspirativas o mensajes discriminatorios.",
            ),
            CourseContent(
                title = "Dificulta la toma de decisiones informadas",
                text = "Ciudadanos expuestos a información falsa pueden apoyar políticas perjudiciales o rechazar avances científicos como las vacunas.",
            ),
            CourseContent(
                title = "Genera caos en situaciones de crisis",
                text = "En desastres naturales, pandemias o conflictos bélicos, la desinformación puede generar pánico, compras de pánico o decisiones erradas.",
            ),
        )
    ),
)