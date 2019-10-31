const promptDirectory = require('inquirer-directory')
module.exports = function(plop) {
    plop.setActionType('echo', function(answers, config, plop) {
        console.log('You have entered: ', answers)
        return true
    });
    plop.setPrompt('directory', promptDirectory)
    plop.setGenerator('code-gen', {
        prompts: [
            {
                type: 'directory',
                basePath: '.',
                name: 'path',
                message: 'Please select a directory',
                default: 'target/'
            },
            {
                message: 'Which template you would like to use?',
                type: 'list',
                name: 'template',
                choices: ['kotlin-jvm'],
                default: 0
            },
            {
                message: 'What is the groupId of the project?',
                type: 'input',
                name: 'groupId',
                default: 'com.github.lawkai'
            },
            {
                message: 'What is the artifactId of the project?',
                type: 'input',
                name: 'artifactId'
            }
        ],
        actions: [
            {
                type: 'echo'
            },
            {
                type: 'addMany',
                base: 'templates/{{template}}',
                templateFiles: '**/*',
                // templateFiles: 'templates/{{template}}/**/*',
                destination: '{{path}}/{{artifactId}}'
            }
        ]
    })
}
