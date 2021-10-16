<?php

/* @var $this yii\web\View */

use yii\helpers\Html;

$this->title = 'About';

?>
<div class="site-about">
    <h1><?= Html::encode($this->title) ?></h1>
    <br>
    <div class="body-content">
        <div class="row">
            <div class="col-lg-6">
                <h2>Goals</h2>

                <p>Development of a website for product management by the application manager and mechanics.
                    Development of an Android application for use of the product by customers and mechanics.
                    Development of a database to store all the information contained both on the website and in the Android application.
                    Development of an API to connect a database to the website and Android application.</p>

             </div>
            <div class="col-lg-6">
                <h2>GitHub and Jira</h2>

                <p>To manage the project, we use Jira and GitHub.</p>
                <p><a class="btn btn-outline-secondary" href="https://github.com/DiogoRCP/PSI_PROJETO_ADG">Git Repository &raquo;</a></p>

                <p><a class="btn btn-outline-secondary" href="https://psi-da-mds-ga.atlassian.net/jira/software/projects/PPA/boards/4/backlog">Jira &raquo;</a></p>
            </div>
        </div>

</div>
