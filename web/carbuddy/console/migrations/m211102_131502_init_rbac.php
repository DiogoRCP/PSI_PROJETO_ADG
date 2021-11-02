<?php

use yii\db\Migration;

/**
 * Class m211102_131502_init_rbac
 */
class m211102_131502_init_rbac extends Migration
{
    /**
     * {@inheritdoc}
     */
    public function safeUp()
    {
        $auth = Yii::$app->authManager;

        //create users
        $admin = $auth->createRole('admin');
        $auth->add($admin);

        $client = $auth->createRole('client');
        $auth->add($client);

        $collaborator = $auth->createRole('collaborator');
        $auth->add($collaborator);

        //Permissions
        $createCompany = $auth->createPermission('createCompany');
        $createCompany->description = 'Create a company';
        $auth->add($createCompany);

        //Atribuições
        $auth->addChild($admin, $createCompany);

    }

    /**
     * {@inheritdoc}
     */
    public function safeDown()
    {
        $auth = Yii::$app->authManager;

        $auth->removeAll();
    }

/*
    // Use up()/down() to run migration code without a transaction.
    public function up()
    {

    }

    public function down()
    {

    }
*/
}
