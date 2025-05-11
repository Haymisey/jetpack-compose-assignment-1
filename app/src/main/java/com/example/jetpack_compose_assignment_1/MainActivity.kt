package com.example.jetpack_compose_assignment_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack_compose_assignment_1.model.Course
import com.example.jetpack_compose_assignment_1.ui.theme.CoursesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoursesAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { padding ->
                    CourseListScreen(
                        courses = sampleCourses,
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}

@Composable
fun CourseListScreen(courses: List<Course>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = courses) { course ->
            CourseCard(course = course)
        }
    }
}

@Composable
fun CourseCard(course: Course) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
                .padding(16.dp)
        ) {
            // Basic info section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = course.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = course.code,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "${course.creditHours} ${stringResource(R.string.credit_hours)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) stringResource(R.string.collapse)
                    else stringResource(R.string.expand),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Expanded details section
            if (expanded) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = course.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (course.prerequisites.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.prerequisites),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        course.prerequisites.forEach { prereq ->
                            Text(
                                text = "• $prereq",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}

// Preview functions
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CourseCardPreview() {
    CoursesAppTheme {
        CourseCard(
            course = Course(
                title = "Introduction to Computer Science",
                code = "CS101",
                creditHours = 3,
                description = "Fundamental concepts of computer science including problem solving, algorithms, and basic programming.",
                prerequisites = listOf("Math 101", "English 101")
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CourseListScreenPreview() {
    CoursesAppTheme {
        CourseListScreen(courses = sampleCourses)
    }
}

// Sample data
val sampleCourses = listOf(
    Course(
        title = "Artificial Intelligence",
        code = "AI301",
        creditHours = 4,
        description = "Introduction to AI concepts including machine learning, neural networks, and natural language processing. Students will learn to build intelligent systems and understand AI algorithms.",
        prerequisites = listOf("Data Structures", "Linear Algebra")
    ),
    Course(
        title = "Mobile App Development",
        code = "MAD401",
        creditHours = 3,
        description = "Learn to develop modern mobile applications using current frameworks and best practices. Covers UI/UX design, state management, and platform-specific features.",
        prerequisites = listOf("Object-Oriented Programming", "Web Development")
    ),
    Course(
        title = "Cloud Computing",
        code = "CC501",
        creditHours = 4,
        description = "Study of cloud platforms, distributed systems, and scalable architecture. Includes hands-on experience with major cloud providers and deployment strategies.",
        prerequisites = listOf("Networking", "Database Systems")
    ),
    Course(
        title = "Cybersecurity",
        code = "SEC401",
        creditHours = 4,
        description = "Comprehensive study of security principles, cryptography, network security, and ethical hacking. Learn to protect systems and data from various threats.",
        prerequisites = listOf("Operating Systems", "Networking")
    ),
    Course(
        title = "Game Development",
        code = "GD301",
        creditHours = 3,
        description = "Introduction to game design and development. Covers game engines, graphics programming, physics simulation, and interactive storytelling.",
        prerequisites = listOf("Computer Graphics", "Data Structures")
    ),
    Course(
        title = "Machine Learning",
        code = "ML401",
        creditHours = 4,
        description = "Advanced study of machine learning algorithms, statistical models, and data analysis. Includes practical projects in pattern recognition and predictive modeling.",
        prerequisites = listOf("Artificial Intelligence", "Statistics")
    )
)