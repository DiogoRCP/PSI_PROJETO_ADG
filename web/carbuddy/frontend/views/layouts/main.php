<?php

/* @var $this \yii\web\View */

/* @var $content string */

use common\widgets\Alert;
use frontend\assets\AppAsset;
use yii\bootstrap4\Breadcrumbs;
use yii\bootstrap4\Html;
use yii\bootstrap4\Nav;
use yii\bootstrap4\NavBar;

AppAsset::register($this);
?>
<?php $this->beginPage() ?>
    <!DOCTYPE html>
    <html lang="<?= Yii::$app->language ?>" class="h-100">
    <head>
        <meta charset="<?= Yii::$app->charset ?>">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <?php $this->registerCsrfMetaTags() ?>
        <title><?= Html::encode($this->title) ?></title>
        <?php $this->head() ?>
    </head>
    <body class="d-flex flex-column h-100">
    <?php $this->beginBody() ?>

    <header>
        <?php
        NavBar::begin([
            'brandLabel' => '<img src="../images/logo_semnome.png" width="30"> Carbuddy',
            'brandUrl' => Yii::$app->homeUrl,
            'options' => [
                'class' => 'navbar navbar-expand-md navbar-dark bg-dark fixed-top',
            ],
        ]);
        $menuItems = [
            //['label' => 'Home', 'url' => ['/site/index']],
            //['label' => 'About', 'url' => ['/site/about']],
            //['label' => 'Contact', 'url' => ['/site/contact']],
        ];
        if (Yii::$app->user->isGuest) {
            $menuItems2 = [
                ['label' => 'Signup', 'url' => ['/site/signup']],
                ['label' => 'Login', 'url' => ['/site/login']]
            ];
        } else {
            $menuItems2 = [Html::tag('li',

                Html::a(Yii::$app->user->identity->username, '#', ['class' => 'nav-link dropdown-toggle',
                    'id' => 'navbarDropdownMenuLink', 'data-toggle' => 'dropdown', 'aria-haspopup' => 'true',
                    'aria-expanded' => 'false']) .

                Html::tag('div', Html::beginForm(['/site/logout'], 'post')

                        // Conteudo

                    . Html::a('Account', ['/user/view'], ['class' => 'dropdown-item'])

                    . Html::a('Contact', ['/site/contact'], ['class' => 'dropdown-item'])

                    . Html::a('About', ['/site/about'], ['class' => 'dropdown-item'])

                    . Html::submitButton('Logout', ['class' => 'logout dropdown-item'])
                    . Html::endForm()

                    , ['class' => 'dropdown-menu dropdown-menu-right', 'aria-labelledby' => 'navbarDropdownMenuLink'])

                , ['class' => 'nav-item dropdown'])];
            if (Yii::$app->user->can('frontendCrudRepair')) {
                $menuItems[] = ['label' => 'Repairs', 'url' => ['/repairs/index']];
            }
            if (Yii::$app->user->can('frontendCrudSchedulesCollaborator')) {
                $menuItems[] = ['label' => 'Schedules Manager', 'url' => ['/schedule/index']];
            }
            $menuItems[] = ['label' => 'Garage', 'url' => ['/car/index']];
            $menuItems[] = ['label' => 'Schedules', 'url' => ['/schedules/index']];
        }
        echo Nav::widget([
            'options' => ['class' => 'navbar-nav navbar-right d-flex align-items-center w-100'],
            'items' => $menuItems,
        ]);
        if ($menuItems2 != null) {
            echo Nav::widget([
                'options' => ['class' => 'navbar-nav navbar-right d-flex align-items-center justify-content-end w-100'],
                'items' => $menuItems2,
            ]);
        }
        NavBar::end();
        ?>
    </header>

    <main role="main" class="flex-shrink-0">
        <div class="container">
            <?= Breadcrumbs::widget([
                'links' => isset($this->params['breadcrumbs']) ? $this->params['breadcrumbs'] : [],
            ]) ?>
            <?= Alert::widget() ?>
            <?= $content ?>
        </div>
    </main>

    <footer class="footer mt-auto py-3 text-muted">
        <div class="container">
            <p class="float-left">&copy; <?= Html::encode("Carbuddy") ?> <?= date('Y') ?></p>
            <p class="float-right"><?= Yii::powered() ?></p>
        </div>
    </footer>

    <?php $this->endBody() ?>
    </body>
    </html>
<?php $this->endPage();
